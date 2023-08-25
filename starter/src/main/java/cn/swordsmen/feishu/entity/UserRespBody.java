package cn.swordsmen.feishu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 飞书用户信息响应体模型
 *
 * @author caiwanghong
 * @date 2023/8/25 15:31
 * @version 1.0
 */
@Data
public class UserRespBody {
    private User user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class User {
        @JSONField(alternateNames="user_id")
        private String userCode;

        @JSONField(alternateNames="name")
        private String userName;
    }
}
