package cn.swordsman.feishu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 飞书云表格模型字段注解
 *
 * @author caiwanghong
 * @date 2023/8/25 15:21
 * @version 1.0
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ExcelField {
    /**
     * 表格中的字段顺序
     */
    int order() default Integer.MAX_VALUE;
}
