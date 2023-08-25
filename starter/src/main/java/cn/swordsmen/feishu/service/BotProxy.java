package cn.swordsmen.feishu.service;

import cn.swordsmen.feishu.entity.CloudFolder;
import cn.swordsmen.feishu.entity.CloudFolderRespBody;
import cn.swordsmen.feishu.entity.UserRespBody;
import com.lark.oapi.core.token.AccessTokenType;
import com.lark.oapi.service.docx.v1.enums.BlockBlockTypeEnum;
import com.lark.oapi.service.docx.v1.model.Block;
import com.lark.oapi.service.docx.v1.model.CreateDocumentBlockChildrenRespBody;
import com.lark.oapi.service.docx.v1.model.CreateDocumentRespBody;
import com.lark.oapi.service.drive.v1.model.BatchQueryMetaRespBody;
import com.lark.oapi.service.drive.v1.model.RequestDoc;
import com.lark.oapi.service.sheets.v3.model.CreateSpreadsheetRespBody;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 飞书机器人对接接口 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/5/23 19:14
 */
public interface BotProxy {
    /***
     * 获取TenantAccessToken [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param tokenType token类型
     * @return TenantAccessTokenResp
     * @date 2023/5/23 21:22
     */
    String getBotAccessToken(AccessTokenType tokenType);

    /***
     * 新建文件夹V1版本 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param folderName 文件夹名
     * @param folderToken 父文件夹token
     * @return 文件夹token
     * @date 2023/5/23 21:23
     */
    String createFolder_V1(String folderName, String folderToken);

    /***
     * 新建文件夹V2版本 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param folderName 文件夹名
     * @param folderToken 父文件夹token
     * @return 文件夹token
     * @date 2023/5/23 21:23
     */
    String createFolder_V2(String folderName, String folderToken);

    /***
     * 根据文件夹token查询文件夹信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param folderToken 文件夹token
     * @return CloudFolderRespBody
     * @date 2023/6/20 19:53
     */
    CloudFolderRespBody queryFolderByToken(String folderToken);

    /***
     * 查询文件夹下指定名称的文件token—V1 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param folderToken 父文件夹token
     * @return 文件夹token
     * @date 2023/5/23 21:23
     */
    String queryFirstFileTokenOfFolderByName_V1(String fileName, String fileType, String folderToken);

    /***
     * 查询文件夹下指定名称的文件token—V2 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param folderToken 父文件夹token
     * @return 文件夹token
     * @date 2023/5/23 21:23
     */
    String queryFirstFileTokenOfFolderByName_V2(String fileName, String fileType, String folderToken);

    /***
     * 创建云表格 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param title 表格标题
     * @param folderToken 父文件夹token
     * @return CreateSpreadsheetRespBody
     * @date 2023/5/23 21:23
     */
    CreateSpreadsheetRespBody createSheet(String title, String folderToken);

    /***
     * 查询云表格sheet页的id [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param spreadsheetToken 云表格的token
     * @return String 云表格sheet页的id
     * @date 2023/5/23 22:45
     */
    String querySheetId(String spreadsheetToken);

    /***
     * 更新云文档权限 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param type 文件类型 sheet/docx
     * @param fileToken 文件token
     * @return void
     * @date 2023/5/23 22:54
     */
    void patchPermission(String type, String fileToken);

    /***
     * 向表格中插入数据 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param spreadSheetToken 云表格token
     * @param data 待插入数据
     * @date 2023/5/23 23:30
     */
    void prependDataToSheet(String spreadSheetToken, String data);

    /***
     * 批量设置云表格单元格样式 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param spreadsheetToken 表格token
     * @param config style配置
     * @return void
     * @date 2023/5/29 15:00
     */
    void batchUpdateSheetStyle(String spreadsheetToken, String config);

    /***
     * 批量设置云表格属性 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param spreadsheetToken 表格token
     * @param properties 表格属性
     * @return void
     * @date 2023/5/29 15:00
     */
    void batchUpdateSheetProperties(String spreadsheetToken, String properties);

    /***
     * 创建云文档 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param title 文档标题
     * @param folderToken 父文件夹token
     * @return CreateDocumentRespBody
     * @date 2023/5/23 21:23
     */
    CreateDocumentRespBody createDocument(String title, String folderToken);

    /***
     * 获取文档元数据 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param requestDocs 待查询文档的token及文档类型信息
     * @return BatchQueryMetaRespBody
     * @date 2023/5/23 23:29
     */
    BatchQueryMetaRespBody batchQueryMeta(RequestDoc... requestDocs);


    /***
     * 云文档创建块 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param documentId 云文档id
     * @param blockId 父块id
     * @param blocks 云文档新增块
     * @return CreateDocumentBlockChildrenRespBody
     * @date 2023/5/24 3:33
     */
    CreateDocumentBlockChildrenRespBody createDocumentBlockChildren(String documentId, String blockId,
                                                                    Integer blockIndex, Block... blocks);

    /***
     * 构建key: value类型文本的文档block [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param key 标题
     * @param values 内容
     * @param isLink 是否链接
     * @param isAtUser 是否at用户
     * @return Block
     * @date 2023/5/25 21:00
     */
    Block buildTextBlock(boolean isLink, boolean isAtUser, String key, String... values);

    /***
     * 构建普通类型文本block [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param content
     * @return Block
     * @date 2023/5/25 22:25
     */
    Block buildTextBlock(String content);

    /***
     * 构建高亮文档block [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param backgroundColor 背景颜色
     * @param borderColor 边框颜色
     * @return Block
     * @date 2023/5/25 21:13
     */
    Block buildCalloutBlock(int backgroundColor, int borderColor);

    /***
     * 构建标题block [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param content 标题内容
     * @param headingType 标题类型 h1-h9
     * @return Block
     * @date 2023/5/30 18:23
     */
    Block buildHeadingBlock(String content, BlockBlockTypeEnum headingType);

    /***
     * 发送问题报告云文档给用户或群组 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param chatType 聊天类型，0:发给用户；1:发给群组
     * @param receivedIds 接受用户或群组id列表
     * @param templateType 消息模板类型，0:分享文档， 1：@我通知
     * @param docxUrl 云文档链接
     * @return void
     * @date 2023/5/24 4:30
     */
    void sendMessages(Integer chatType, List<String> receivedIds, Integer templateType, String docxUrl);

    /***
     * 并发情况下创建新文件夹，并返回文件夹信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote 创建新文件夹，并返回文件夹token
     * @param executor 异步线程池
     * @return CloudFolder 新文件夹信息
     * @date 2023/6/19 10:04
     */
    CloudFolder getCloudFolder(ExecutorService executor);

    /***
     * 根据天书用户的openid列表查询用户信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote 根据天书用户的openid列表查询用户信息
     * @param openIds 用户的openId列表
     * @return List<User>
     * @date 2023/6/21 15:15
     */
    List<UserRespBody.User> queryUserInfoByOpenIds(List<String> openIds);
}
