package cn.swordsmen.feishu.entity;

import com.lark.oapi.core.response.BaseResponse;
import lombok.Data;

/**
 * 天书自建应用鉴权token
 *
 * @author caiwanghong
 * @date 2023/8/25 15:25
 * @version 1.0
 */
@Data
public class BotAccessTokenResp extends BaseResponse<BotAccessTokenResp> {
    /**
     * 过期时间ms
     */
    private int expire;

    /**
     * 企业鉴权token
     */
    private String tenantAccessToken;

    /**
     * 应用鉴权token
     */
    private String appAccessToken;
}
