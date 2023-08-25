package cn.swordsmen.enums;

import cn.swordsmen.exception.custom.BusinessException;

/**
 * 业务异常枚举父接口 [蔡旺鸿]
 *
 * @Author caiwanghong
 * @Date 2023/8/24 13:37
 * @Version 1.0
 * @apiNote
 */
public interface ErrorEnumBase<T extends Integer, R extends String> {
    /* 错误码 */
    T getCode();

    /* 异常信息 */
    R getMsg();

    /***
     * 自定义业务异常抛出 [蔡旺鸿]
     *
     * @author caiwanghong
     * @date 2023/8/24 13:57
     */
    default void throwError() {
        throw new BusinessException(getCode(), getMsg());
    }
}
