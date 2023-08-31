package cn.swordsman.exception.annotation;

import cn.swordsman.exception.config.ExceptionAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 全局异常处理开关
 *
 * @author caiwanghong
 * @date 2023/8/25 14:14
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ExceptionAutoConfiguration.class)
@Documented
public @interface EnableGlobalExceptionHandler {
}
