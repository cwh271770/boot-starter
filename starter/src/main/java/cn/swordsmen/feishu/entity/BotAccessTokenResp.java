package cn.swordsmen.feishu.entity;

import com.lark.oapi.core.response.BaseResponse;
import lombok.Data;

/**
 * 天书自建应用鉴权token [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/6/14 13:47
 */
@Data
public class BotAccessTokenResp extends BaseResponse {
    /* 过期时间ms */
    private int expire;

    /* 企业鉴权token */
    private String tenantAccessToken;

    /* 应用鉴权token */
    private String appAccessToken;
}
