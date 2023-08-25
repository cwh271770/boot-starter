package cn.swordsmen.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * 用户信息封装
 *
 * @author caiwanghong
 * @date 2023/8/25 15:44
 * @version 1.0
 */
@Data
@Builder
public class User {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户ip地址
     */
    private String ip;

    /**
     * 其他扩展属性
     */
    @Singular("addProperty")
    private Map<String, Object> properties;
}
