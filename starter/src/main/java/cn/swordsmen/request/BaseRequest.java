package cn.swordsmen.request;

import cn.swordsmen.exception.custom.ArgumentException;

import java.io.Serializable;

/**
 * request全局基类 [蔡旺鸿]
 *
 * @author caiwanghong
 * @date 2023/8/22 16:33
 */
public interface BaseRequest extends Serializable {
    /**
     * 请求体自定义校验方法
     */
    void validate() throws ArgumentException;
}
