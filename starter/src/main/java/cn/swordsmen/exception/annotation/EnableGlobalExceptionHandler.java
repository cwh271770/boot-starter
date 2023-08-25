package cn.swordsmen.exception.annotation;

import cn.swordsmen.exception.config.ExceptionAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/***
 * 全局异常处理开关 [蔡旺鸿]
 *
 * @author caiwanghong
 * @date 2023/8/23 17:03
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ExceptionAutoConfiguration.class)
@Documented
public @interface EnableGlobalExceptionHandler {
}
