package cn.swordsmen.feishu.entity;

import cn.swordsmen.exception.custom.BusinessException;
import cn.swordsmen.feishu.annotation.ExcelField;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 飞书云表格模型基础类 [蔡旺鸿]
 *
 * @Author caiwanghong
 * @Date 2023/6/13 15:08
 * @Version 1.0
 * @apiNote
 */
@Getter
@Setter
public abstract class CloudExcelBase {
    /***
     * 收集实体对象所有属性值到数组 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @return Object[]
     * @date 2023/6/13 15:10
     */
    public Object[] toObjArray() {
        Field[] fields = Arrays.stream(this.getClass().getDeclaredFields())
            .filter(field -> !field.isSynthetic() && field.getAnnotation(ExcelField.class) != null) // 过滤出原生且包含ExcelField注解的属性
            .sorted(Comparator.comparing(field -> field.getAnnotation(ExcelField.class).order()))
            .toArray(Field[]::new);
        Object[] value = new Object[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                value[i] = field.get(this);
            }
        } catch (IllegalAccessException e) {
            throw new BusinessException(e);
        }

        return value;
    }
}

