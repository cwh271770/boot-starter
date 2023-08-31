package cn.swordsman.request;

import cn.swordsman.exception.custom.ArgumentException;

import java.io.Serializable;

/**
 * request全局基类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:41
 * @version 1.0
 */
public interface BaseRequest extends Serializable {
    /**
     * 请求体自定义校验方法
     */
    void validate() throws ArgumentException;
}
