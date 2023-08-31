package cn.swordsman.exception.custom;

import lombok.Getter;

import static cn.swordsman.response.RespStatus.HTTP_STATUS_500;

/**
 * 自定义业务异常封装
 *
 * @author caiwanghong
 * @date 2023/8/25 14:15
 * @version 1.0
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 7284307877988205542L;

    private int code = HTTP_STATUS_500.getCode();

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
    }
    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BusinessException(Integer code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public BusinessException(String msg, Integer code, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
    }

    protected BusinessException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4);
    }

}
