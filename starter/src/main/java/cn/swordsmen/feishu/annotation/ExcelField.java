package cn.swordsmen.feishu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  飞书云表格模型字段注解 [蔡旺鸿]
 *
 * @Author caiwanghong
 * @Date 2023/7/6 15:08
 * @Version 1.0
 * @apiNote
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ExcelField {
    /**
     * 表格中的字段顺序
     */
    int order() default Integer.MAX_VALUE;
}
