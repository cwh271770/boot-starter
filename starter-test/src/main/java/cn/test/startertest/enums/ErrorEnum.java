package cn.test.startertest.enums;

import cn.swordsmen.enums.ErrorEnumBase;

/**
 * 错误信息枚举
 *
 * @author caiwanghong
 * @date 2023/8/25 15:49
 * @version 1.0
 */
public enum ErrorEnum implements ErrorEnumBase<Integer, String> {
    DATA_IS_NOT_EXIST(2023, "数据不存在");

    private final Integer code;

    private final String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
