package cn.swordsmen.feishu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.swordsmen.exception.custom.BusinessException;
import cn.swordsmen.feishu.entity.*;
import cn.swordsmen.feishu.service.BotProxy;
import cn.swordsmen.feishu.properties.BotProperties;
import cn.swordsmen.lock.utils.LockUtil;
import cn.swordsmen.user.entity.User;
import cn.swordsmen.user.utils.UserUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.token.AccessTokenType;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.core.utils.Lists;
import com.lark.oapi.service.authen.v1.model.CreateAccessTokenResp;
import com.lark.oapi.service.authen.v1.model.CreateAccessTokenRespBody;
import com.lark.oapi.service.docx.v1.enums.BlockBlockTypeEnum;
import com.lark.oapi.service.docx.v1.enums.TextElementStyleFontColorEnum;
import com.lark.oapi.service.docx.v1.model.*;
import com.lark.oapi.service.docx.v1.model.TextRun;
import com.lark.oapi.service.drive.v1.model.*;
import com.lark.oapi.service.drive.v1.model.File;
import com.lark.oapi.service.sheets.v3.model.CreateSpreadsheetResp;
import com.lark.oapi.service.sheets.v3.model.CreateSpreadsheetRespBody;
import com.lark.oapi.service.sheets.v3.model.QuerySpreadsheetSheetResp;
import com.lark.oapi.service.sheets.v3.model.Spreadsheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.swordsmen.feishu.constant.Constant.Feishu_Card_Template.AT_USER_TEMPLATE;
import static cn.swordsmen.feishu.constant.Constant.Feishu_Card_Template.SHARE_TEMPLATE;
import static cn.swordsmen.feishu.constant.Constant.Redis_Key.*;

/**
 * 飞书机器人对接接口实现类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:33
 * @version 1.0
 */
@Slf4j
@Lazy
@Service
public class BotProxyImpl implements BotProxy {
    @Autowired
    private BotProperties botProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LockTemplate lockTemplate;

    public String getBotAccessToken(AccessTokenType tokenType) {
        User currentUser = UserUtil.currentUser();
        String tokenKey = (tokenType == AccessTokenType.User) ? (AccessTokenType.User +":"+ currentUser.getUserCode()) :
            tokenType.toString();
        String accessToken = stringRedisTemplate.opsForValue().get(tokenKey);
        if (accessToken != null) {
            return accessToken;
        }

        if (tokenType != AccessTokenType.User) {
            String url = botProperties.getDomain() + "/open-apis/auth/v3/app_access_token/internal/";
            String jsonStr = "{\"app_id\":\""+botProperties.getAppId()+"\",\"app_secret\":\""+botProperties.getAppSecret()+"\"}";
            String result = HttpUtil.post(url, jsonStr);
            BotAccessTokenResp tokenResp = JSON.parseObject(result, BotAccessTokenResp.class);
            if (tokenResp == null || !tokenResp.success()) {
                log.error("获取accessToken失败");
                throw new RuntimeException("获取accessToken失败");
            }

            int expire = tokenResp.getExpire() - 200;
            String tenantAccessToken = tokenResp.getTenantAccessToken();
            String appAccessToken = tokenResp.getAppAccessToken();
            stringRedisTemplate.opsForValue().set(AccessTokenType.Tenant.toString(), tenantAccessToken, expire, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(AccessTokenType.App.toString(), appAccessToken, expire, TimeUnit.SECONDS);

            return tokenType == AccessTokenType.Tenant ? tenantAccessToken : appAccessToken;
        } else {
            String url = "/open-apis/authen/v1/access_token";
            String jsonStr = "{\"grant_type:\"authorization_code\",\"code\":\""+ UserUtil.getProperty("code") +"\"}";
            CreateAccessTokenResp tokenResp = sendHttpRequest(url, jsonStr, Method.POST, AccessTokenType.App, CreateAccessTokenResp.class);
            CreateAccessTokenRespBody data = tokenResp.getData();
            int expire = data.getExpiresIn() - 200;
            stringRedisTemplate.opsForValue().set(tokenKey, data.getAccessToken(), expire, TimeUnit.SECONDS);

            return data.getAccessToken();
        }
    }

    @Override
    public String createFolder_V1(String folderName, String folderToken) {
        String url = "/open-apis/drive/v1/files/create_folder";
        String jsonStr = "{\"name\":\""+folderName+"\",\"folder_token\":\""+folderToken+"\"}";
        CreateFolderFileResp resp = sendHttpRequest(url, jsonStr, Method.POST, AccessTokenType.Tenant, CreateFolderFileResp.class);
        return resp.getData().getToken();
    }

    @Override
    public String createFolder_V2(String folderName, String folderToken) {
        String url = "/open-apis/drive/explorer/v2/folder/" + folderToken;
        String jsonStr = "{\"title\":\""+folderName+"\"}";
        CreateFolderFileResp resp = sendHttpRequest(url, jsonStr, Method.POST, AccessTokenType.Tenant, CreateFolderFileResp.class);
        return resp.getData().getToken();
    }

    @Override
    public CloudFolderRespBody queryFolderByToken(String folderToken) {
        String url =  String.format("/open-apis/drive/explorer/v2/folder/%s/meta", folderToken);
        CloudFolderResp resp = sendHttpRequest(url, null, Method.GET, AccessTokenType.Tenant, CloudFolderResp.class);
        return resp.getData();
    }

    @Override
    public String queryFirstFileTokenOfFolderByName_V1(String fileName, String fileType, String folderToken) {
        String url = "/open-apis/drive/v1/files?page_size=200&page_token=%s&folder_token=" + folderToken;
        String pageToken = null;
        String fileToken = null;
        while(true) {
            // 1.构建url
            String getUrl = String.format(url, pageToken);
            // 2.发起请求
            ListFileResp resp = sendHttpRequest(getUrl, null, Method.GET, AccessTokenType.Tenant, ListFileResp.class);
            ListFileRespBody data = resp.getData();
            pageToken = data.getNextPageToken();
            fileToken = Arrays.stream(data.getFiles())
                .filter(file -> StrUtil.equals(file.getName(), fileName) && StrUtil.equals(file.getType(), fileType))
                .map(File::getToken)
                .sorted()
                .findFirst()
                .orElse(null);

            if (fileToken != null || BooleanUtil.isFalse(data.getHasMore())) {
                break;
            }
        }

        return fileToken;
    }

    @Override
    public String queryFirstFileTokenOfFolderByName_V2(String fileName, String fileType, String folderToken) {
        String url = String.format("/open-apis/drive/explorer/v2/folder/%s/children?types=%s", folderToken, fileType);
        CloudFilesResp resp = sendHttpRequest(url, null, Method.GET, AccessTokenType.Tenant, CloudFilesResp.class);
        Collection<CloudFilesRespBody.CloudFile> files = resp.getData().getChildren().values();

        return files.stream()
            .filter(file -> StrUtil.equals(file.getName(), fileName))
            .map(CloudFilesRespBody.CloudFile::getToken)
            .sorted()
            .findFirst()
            .orElse(null);
    }

    @Override
    public CreateSpreadsheetRespBody createSheet(String title, String folderToken) {
        String url = "/open-apis/sheets/v3/spreadsheets";
        Spreadsheet req = Spreadsheet.newBuilder()
            .title(title)
            .folderToken(folderToken)
            .build();
        CreateSpreadsheetResp resp =
            sendHttpRequest(url, Jsons.DEFAULT.toJson(req), Method.POST, AccessTokenType.Tenant, CreateSpreadsheetResp.class);

        if (!resp.success()) {
            // 文件夹下文件超上限
            if (resp.getCode() == 1062507) {
                // 创建新文件夹，然后在新文件夹下创建表格
                log.warn("云文件夹下文件数量超上限！");
                CloudFolder newFolder = acquireNewCloudFolder();
                return createSheet(title, newFolder.getFolderToken());
            }
        }

        // 文件数+1
        incrementCloudFileCount(folderToken);
        return resp.getData();
    }

    public String querySheetId(String spreadsheetToken) {
        String url = String.format("/open-apis/sheets/v3/spreadsheets/%s/sheets/query", spreadsheetToken);
        QuerySpreadsheetSheetResp resp = sendHttpRequest(url, null, Method.GET, AccessTokenType.Tenant, QuerySpreadsheetSheetResp.class);
        return Arrays.stream(resp.getData().getSheets())
            .findFirst().get().getSheetId();
    }

    @Override
    public void prependDataToSheet(String spreadSheetToken, String data) {
        String url = String.format("/open-apis/sheets/v2/spreadsheets/%s/values_prepend", spreadSheetToken);
        sendHttpRequest(url, data, Method.POST, AccessTokenType.Tenant, BaseResponse.class);
    }

    @Override
    public void batchUpdateSheetStyle(String spreadsheetToken, String config) {
        String url = String.format("/open-apis/sheets/v2/spreadsheets/%s/styles_batch_update", spreadsheetToken);
        sendHttpRequest(url, config, Method.PUT, AccessTokenType.Tenant, BaseResponse.class);
    }

    @Override
    public void batchUpdateSheetProperties(String spreadsheetToken, String properties) {
        String url = String.format("/open-apis/sheets/v2/spreadsheets/%s/sheets_batch_update", spreadsheetToken);
        sendHttpRequest(url, properties, Method.POST, AccessTokenType.Tenant, BaseResponse.class);
    }

    @Override
    public void patchPermission(String type, String fileToken) {
        String url = String.format("/open-apis/drive/v1/permissions/%s/public?type=%s", fileToken, type);
        // 创建请求对象
        PermissionPublicRequest req = PermissionPublicRequest.newBuilder()
            .externalAccess(true)
            .securityEntity("anyone_can_view")
            .commentEntity("anyone_can_view")
            .shareEntity("only_full_access")
            .linkShareEntity("docx".equalsIgnoreCase(type) ? "anyone_editable" : "anyone_readable")
            .inviteExternal(true)
            .build();

        // 发起请求
        sendHttpRequest(url, Jsons.DEFAULT.toJson(req), Method.PATCH, AccessTokenType.Tenant, PatchPermissionPublicResp.class);
    }

    @Override
    public CreateDocumentRespBody createDocument(String title, String folderToken) {
        String url = "/open-apis/docx/v1/documents";
        CreateDocumentReqBody req = CreateDocumentReqBody.newBuilder()
            .folderToken(folderToken)
            .title(title)
            .build();
        CreateDocumentResp resp = sendHttpRequest(url, Jsons.DEFAULT.toJson(req), Method.POST, AccessTokenType.Tenant,
            CreateDocumentResp.class);
        if (!resp.success()) {
            // 文件夹下文件超上限
            if (resp.getCode() == 1062507) {
                // 创建新文件夹，然后在新文件夹下创建文档
                log.warn("云文件夹下文件数量超上限！");
                CloudFolder newFolder = acquireNewCloudFolder();
                return createDocument(title, newFolder.getFolderToken());
            }
        }

        // 文件数+1
        incrementCloudFileCount(folderToken);
        return resp.getData();
    }

    private void incrementCloudFileCount(String folderToken) {
        String fileFolderStr = stringRedisTemplate.opsForValue().get(CLOUD_FOLDER_FILE);
        CloudFolder cloudFolder = CloudFolder.toFolder(fileFolderStr);
        if (cloudFolder != null && StrUtil.equals(cloudFolder.getFolderToken(), folderToken)) {
            // 文件数+1
            stringRedisTemplate.opsForValue().increment(CLOUD_FILE_COUNT);
        }
    }

    @Override
    public BatchQueryMetaRespBody batchQueryMeta(RequestDoc... requestDocs) {
        String url = "/open-apis/drive/v1/metas/batch_query";
        MetaRequest req = MetaRequest.newBuilder()
            .requestDocs(requestDocs)
            .withUrl(true)
            .build();

        // 发起请求
        BatchQueryMetaResp resp = sendHttpRequest(url, Jsons.DEFAULT.toJson(req), Method.POST, AccessTokenType.Tenant,
            BatchQueryMetaResp.class);
        return resp.getData();
    }

    @Override
    public CreateDocumentBlockChildrenRespBody createDocumentBlockChildren(String documentId, String blockId,
                                                                           Integer blockIndex, Block... blocks) {
        String url = String.format("/open-apis/docx/v1/documents/%s/blocks/%s/children?document_revision_id=-1&user_id_type=user_id",
            documentId, Optional.ofNullable(blockId).orElse(documentId));
        CreateDocumentBlockChildrenReqBody req = CreateDocumentBlockChildrenReqBody.newBuilder()
            .children(blocks)
            .index(blockIndex)
            .build();

        // 发起请求
        CreateDocumentBlockChildrenResp resp =
            sendHttpRequest(url, Jsons.DEFAULT.toJson(req), Method.POST, AccessTokenType.Tenant, CreateDocumentBlockChildrenResp.class);
        return resp.getData();
    }

    @Override
    public List<UserRespBody.User> queryUserInfoByOpenIds(List<String> openIds) {
        List<UserRespBody.User> userList = Lists.newArrayList();
        for (String openId : openIds) {
            String url = String.format("/open-apis/contact/v3/users/%s?user_id_type=open_id", openId);
            UserResp userResp = sendHttpRequest(url, null, Method.GET, AccessTokenType.Tenant, UserResp.class);
            userList.add(userResp.getData().getUser());
        }
        return userList;
    }

    @Override
    public void sendMessages(Integer chatType, List<String> receivedIds, Integer templateType, String docxUrl) {
        String cardContent = buildCardContent(templateType, docxUrl);
        if (chatType == 0) {
            // 批量给用户发送文档链接
            batchSendMsgToUser(receivedIds, cardContent);
        } else if (chatType == 1) {
            // 给群组发送文档链接
            for (String chatId : receivedIds) {
                sendMsgToGroupChat(chatId, cardContent);
            }
        } else {
            String errMsg = String.format("会话类型错误, chatType：%s", chatType);
            log.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        }
    }

    private void batchSendMsgToUser(List<String> userIds, String content) {
        String userUrl = "/open-apis/message/v4/batch_send";
        JSONObject param = new JSONObject();
        param.put("user_ids", userIds);
        param.put("msg_type", "interactive");
        param.put("card", JSONObject.parse(content));
        sendHttpRequest(userUrl, JSONObject.toJSONString(param), Method.POST, AccessTokenType.Tenant, BaseResponse.class);
    }

    private void sendMsgToGroupChat(String chatId, String content) {
        String chatUrl = "/open-apis/im/v1/messages?receive_id_type=chat_id";
        JSONObject param = new JSONObject();
        param.put("receive_id", chatId);
        param.put("msg_type", "interactive");
        param.put("uuid", UUID.randomUUID().toString());
        param.put("content", content);
        sendHttpRequest(chatUrl, JSONObject.toJSONString(param), Method.POST, AccessTokenType.Tenant, BaseResponse.class);
    }

    private String buildCardContent(Integer templateType, String docxUrl) {
        String userName = UserUtil.currentUser().getUserName();
        String date = DateUtil.format(LocalDateTime.now(), "yyyy年MM月dd日");
        return templateType == 1 ? String.format(AT_USER_TEMPLATE, userName, docxUrl, date)
            : String.format(SHARE_TEMPLATE, docxUrl, date);
    }

    @Override
    public Block buildTextBlock(boolean isLink, boolean isAtUser, String key, String... values) {
        List<TextElement> elements = new ArrayList<>();
        // 1.行首若存在标题，则加粗处理
        Optional.ofNullable(key)
            .map(this::buildBoldTextElement)
            .ifPresent(elements::add);

        if (isLink) {
            // 2.link类型文本块
            if (values.length >= 2) {
                TextElement linkText = buildLinkTextElement(values[0], values[1]);
                elements.add(linkText);
            }
        } else if (isAtUser) {
            // 3.at类型
            if (values.length >= 1) {
                elements.addAll(buildAtUserTextElement(values));
            }
        } else {
            // 4.普通类型
            if (values.length >= 1) {
                elements.add(buildCommonTextElement(values[0]));
            }
        }

        return Block.newBuilder()
            .blockType(BlockBlockTypeEnum.TEXT)
            .text(Text.newBuilder()
                .elements(elements.toArray(new TextElement[elements.size()]))
                .build())
            .build();
    }

    private TextElement buildCommonTextElement(String content) {
        return TextElement.newBuilder()
            .textRun(TextRun.newBuilder()
                .content(content)
                .build())
            .build();
    }

    private TextElement buildBoldTextElement(String content) {
        return TextElement.newBuilder()
            .textRun(TextRun.newBuilder()
                .content(content)
                .textElementStyle(TextElementStyle.newBuilder()
                    .bold(true)
                    .build())
                .build())
            .build();
    }

    private List<TextElement> buildAtUserTextElement(String[] userIds) {
        List<TextElement> elements = Lists.newArrayList();
        for (String userId : userIds) {
            elements.add(TextElement.newBuilder()
                .mentionUser(MentionUser.newBuilder()
                    .userId(userId)
                    .textElementStyle(TextElementStyle.newBuilder()
                        .textColor(TextElementStyleFontColorEnum.BLUE).build())
                    .build())
                .build());
        }

        return elements;
    }

    private TextElement buildLinkTextElement(String content, String url) {
        return TextElement.newBuilder()
            .textRun(TextRun.newBuilder()
                .content(content)
                .textElementStyle(TextElementStyle.newBuilder()
                    .link(Link.newBuilder()
                        .url(url)
                        .build())
                    .textColor(TextElementStyleFontColorEnum.BLUE).build())
                .build())
            .build();
    }

    @Override
    public Block buildTextBlock(String content) {
        return Block.newBuilder()
            .blockType(BlockBlockTypeEnum.TEXT)
            .text(Text.newBuilder()
                .elements(new TextElement[]{buildCommonTextElement(content)})
                .build())
            .build();
    }

    @Override
    public Block buildHeadingBlock(String content, BlockBlockTypeEnum headingType) {
        Text text = Text.newBuilder().elements(new TextElement[]{buildCommonTextElement(content)}).build();
        Block block = Block.newBuilder().blockType(headingType).build();
        switch (headingType) {
            case HEADING1:
                block.setHeading1(text);
                break;
            case HEADING2:
                block.setHeading2(text);
                break;
            case HEADING3:
                block.setHeading3(text);
                break;
            case HEADING4:
                block.setHeading4(text);
                break;
            case HEADING5:
                block.setHeading5(text);
                break;
            case HEADING6:
                block.setHeading6(text);
                break;
            case HEADING7:
                block.setHeading7(text);
                break;
            case HEADING8:
                block.setHeading8(text);
                break;
            case HEADING9:
                block.setHeading9(text);
                break;
            default:
                throw new IllegalArgumentException("blockType参数错误, 非标题类型");
        }

        return block;
    }

    @Override
    public Block buildCalloutBlock(int backgroundColor, int borderColor) {
        return Block.newBuilder()
            .blockType(BlockBlockTypeEnum.CALLOUT)
            .callout(Callout.newBuilder()
                .backgroundColor(backgroundColor)
                .borderColor(borderColor)
                .emojiId("pushpin")
                .build())
            .build();
    }

    @Override
    public CloudFolder getCloudFolder(ExecutorService executor) {
        String fileFolderStr = stringRedisTemplate.opsForValue().get(CLOUD_FOLDER_FILE);
        if (fileFolderStr == null) {
            return acquireNewCloudFolder();
        } else {
            // 文件夹下文件数量超过设定上限，异步创建新文件夹
            if (isFileNumExceedLimit()) {
                CompletableFuture.runAsync(this::acquireNewCloudFolder, executor);
            }
        }

        return CloudFolder.toFolder(fileFolderStr);
    }

    private CloudFolder acquireNewCloudFolder() {
        String lockName = LOCK_CLOUD_FOLDER_PREFIX + "new_folder";
        LockInfo lock = LockUtil.lock(lockName);
        String fileFolderStr;
        try {
            fileFolderStr = stringRedisTemplate.opsForValue().get(CLOUD_FOLDER_FILE);
            // 二次判空，避免并发多次创建文件夹
            if (fileFolderStr == null) {
                return createNewCloudFolder();
            } else {
                // 二次判断文件数量是否超过上限，避免并发多次创建文件夹
                if (isFileNumExceedLimit()) {
                    return createNewCloudFolder();
                }
            }
        } finally {
            LockUtil.unlock(lock);
        }

        return CloudFolder.toFolder(fileFolderStr);
    }

    private boolean isFileNumExceedLimit() {
        String count = stringRedisTemplate.opsForValue().get(CLOUD_FILE_COUNT);
        Long fileNum = Optional.ofNullable(count).map(Long::valueOf).orElse(0L);
        return fileNum >= 1490;
    }

    private CloudFolder createNewCloudFolder() {
        Date current = new Date();
        String monthFolderName = DateUtil.format(current, "yyyyMM");
        String fileFolderName = String.valueOf(System.currentTimeMillis());
        String monthFolderStr = stringRedisTemplate.opsForValue().get(CLOUD_FOLDER_MONTH);
        CloudFolder fileFolder;
        if (monthFolderStr == null) {
            // redis也不存在月文件夹, 则新建月文件夹及其子文件夹
            CloudFolder monthFolder = CloudFolder.builder()
                    .folderToken(createFolder_V2(monthFolderName, botProperties.getRootFolderToken()))
                    .folderName(monthFolderName)
                    .build();
            fileFolder = CloudFolder.builder()
                    .folderToken(createFolder_V2(fileFolderName, monthFolder.getFolderToken()))
                    .folderName(fileFolderName)
                    .build();
            // 将月文件夹及其子文件夹token都存在redis中
            stringRedisTemplate.opsForValue().set(CLOUD_FOLDER_MONTH, monthFolder.toString());
            stringRedisTemplate.opsForValue().set(CLOUD_FOLDER_FILE, fileFolder.toString());
        } else {
            // redis存在月文件夹token，则在月文件夹下创建一个新的子文件夹，并将子文件夹token存到redis
            CloudFolder monthFolder = CloudFolder.toFolder(monthFolderStr);
            fileFolder = CloudFolder.builder()
                    .folderToken(createFolder_V2(fileFolderName, monthFolder.getFolderToken()))
                    .folderName(fileFolderName)
                    .build();

            stringRedisTemplate.opsForValue().set(CLOUD_FOLDER_FILE, fileFolder.toString());
        }

        // 重置文件夹下文件数量
        stringRedisTemplate.opsForValue().set(CLOUD_FILE_COUNT, "0");

        return fileFolder;
    }

    private <T extends BaseResponse> T sendHttpRequest(String url, String jsonStr, Method reqMethod,
                                                       AccessTokenType tokenType, Class<T> clazz) {
        String tenantAccessToken = this.getBotAccessToken(tokenType);
        url = botProperties.getDomain() + url;
        HttpRequest httpRequest = null;
        switch (reqMethod) {
            case POST:
                httpRequest = HttpRequest.post(url);
                break;
            case GET:
                httpRequest = HttpRequest.get(url);
                break;
            case PUT:
                httpRequest = HttpRequest.put(url);
                break;
            case PATCH:
                httpRequest = HttpRequest.patch(url);
                break;
            case DELETE:
                httpRequest = HttpRequest.delete(url);
                break;
            default:
                throw new IllegalArgumentException("Http请求方式错误");
        }

        String body = httpRequest.header(Header.CONTENT_TYPE, "application/json; charset=utf-8")
                .header(Header.AUTHORIZATION, String.format("Bearer %s", tenantAccessToken))
                .body(jsonStr)
                .execute()
                .body();
        T result = JSON.parseObject(body, clazz);
        if (result == null) {
            log.error("Http返回消息为空");
            throw new BusinessException("Http返回消息为空");
        }

        if (!result.success() && result.getCode() != 1062507) {
            // 处理服务端错误
            log.error(String.format("errorCode:%s, msg:%s", result.getCode(), result.getMsg()));
            throw new BusinessException(result.getMsg());
        }

        return result;
    }

}
