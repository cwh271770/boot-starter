package cn.swordsman.enums;

import cn.swordsman.exception.custom.BusinessException;

/**
 * 业务异常枚举父接口
 *
 * @author caiwanghong
 * @date 2023/8/25 14:13
 * @version 1.0
 */
public interface ErrorEnumBase<T extends Integer, R extends String> {
    /**
     * 错误码
     */
    T getCode();

    /**
     * 异常信息
     */
    R getMsg();

    /**
     * 自定义业务异常抛出
     *
     * @author caiwanghong
     * @date 2023/8/25 14:13
     */
    default void throwError() {
        throw new BusinessException(getCode(), getMsg());
    }
}
