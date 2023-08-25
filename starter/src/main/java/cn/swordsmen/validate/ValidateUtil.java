package cn.swordsmen.validate;

import cn.hutool.core.util.ArrayUtil;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/23 21:46
 */
public abstract class ValidateUtil {
    private static final Validator validator;

    static {
        validator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true) // 开启快速失败模式：仅仅返回第一条错误信息; false返回所有错误
            .buildValidatorFactory()
            .getValidator();
    }


    /***
     * 模型校验 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param obj
     * @param groups
     * @return Set<ConstraintViolation<T>>
     * @date 2023/8/23 21:48
     */
    public static <T> Set<ConstraintViolation<T>> validate(T obj, Class... groups) {
        Set<ConstraintViolation<T>> constraintViolations;
        if (ArrayUtil.isNotEmpty(groups)){
            constraintViolations = validator.validate(obj, groups);
        } else {
            constraintViolations = validator.validate(obj);
        }

        return constraintViolations;
    }
}
