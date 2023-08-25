package cn.swordsmen.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 响应状态枚举 [蔡旺鸿]
 *
 * @author caiwanghong
 * @date 2023/8/22 13:23
 */
@Getter
@AllArgsConstructor
public enum RespStatus {

    SUCCESS(200, "Success"),
    FAIL(500, "Internal Server Error"),
    INVALID_PARAM(400, "Invalid Parameter"),

    HTTP_STATUS_200(200, "Ok"),
    HTTP_STATUS_400(400, "Bad Request"),
    HTTP_STATUS_401(401, "Unauthorized"),
    HTTP_STATUS_500(500, "Internal Server Error");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

}