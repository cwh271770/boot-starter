package cn.swordsmen.response;

import cn.hutool.core.date.DateUtil;
import lombok.Builder;
import lombok.Data;

/***
 * 全局响应结果封装 [蔡旺鸿]
 *
 * @author caiwanghong
 * @date 2023/8/22 13:25
 */
@Data
@Builder
public class RespResult<T> {
    /**
     * 响应时间
     */
    @Builder.Default
    private String timestamp = DateUtil.now();

    /**
     * 响应状态码
     */
    private Integer status;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /***
     * response success result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> success() {
        return success(null);
    }

    /***
     * response success result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> success(T data) {
        return RespResult.<T>builder().data(data)
                .message(RespStatus.SUCCESS.getDesc())
                .status(RespStatus.SUCCESS.getCode())
                .build();
    }

    /***
     * response error result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> fail() {
        return fail(null, RespStatus.FAIL);
    }

    /***
     * response error result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> fail(T data) {
        return fail(data, RespStatus.FAIL);
    }

    /***
     * response error result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> fail(T data, String message) {
        return fail(data, message, RespStatus.FAIL.getCode());
    }

    /***
     * response error result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> fail(T data, String message, Integer code) {
        return RespResult.<T>builder().data(data)
                .message(message)
                .status(code)
                .build();
    }

    /***
     * response error result wrapper [蔡旺鸿]
     *
     * @author caiwanghong
     * @return RespResult<T>
     * @date 2023/8/22 13:45
     */
    public static <T> RespResult<T> fail(T data, RespStatus status) {
        return RespResult.<T>builder().data(data)
                .message(status.getDesc())
                .status(status.getCode())
                .build();
    }

}