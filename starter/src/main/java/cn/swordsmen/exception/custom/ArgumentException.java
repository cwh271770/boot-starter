package cn.swordsmen.exception.custom;

import lombok.Getter;

import static cn.swordsmen.response.RespStatus.HTTP_STATUS_400;

/**
 * 自定义请求参数异常 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/24 10:58
 */
@Getter
public class ArgumentException extends IllegalArgumentException {
    private static final long serialVersionUID = 8277730051955541442L;

    private int code = HTTP_STATUS_400.getCode();

    public ArgumentException() {
    }

    public ArgumentException(String msg) {
        super(msg);
    }

    public ArgumentException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ArgumentException(Throwable throwable) {
        super(throwable);
    }

    public ArgumentException(Integer code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ArgumentException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ArgumentException(String msg, Integer code, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
    }
}
