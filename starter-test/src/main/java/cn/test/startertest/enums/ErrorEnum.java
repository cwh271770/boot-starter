package cn.test.startertest.enums;

import cn.swordsmen.enums.ErrorEnumBase;

/**
 * Description: [蔡旺鸿]
 *
 * @Author caiwanghong
 * @Date 2023/8/24 13:58
 * @Version 1.0
 * @apiNote
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
