package cn.swordsmen.feishu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 飞书用户信息响应体模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/6/21 17:02
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
