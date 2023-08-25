package cn.swordsmen.validate;

import cn.hutool.core.util.ArrayUtil;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:47
 * @version 1.0
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


    /**
     * 模型校验
     *
     * @author caiwanghong
     * @param obj 校验对象
     * @param groups 分组信息
     * @date 2023/8/25 15:47
     * @return Set<ConstraintViolation<T>>
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
