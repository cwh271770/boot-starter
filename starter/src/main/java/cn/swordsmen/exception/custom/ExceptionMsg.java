package cn.swordsmen.exception.custom;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * 异常信息封装 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/22 14:09
 */
@Data
@Builder
public class ExceptionMsg {
    @Singular
    private final List<String> errors;

}
