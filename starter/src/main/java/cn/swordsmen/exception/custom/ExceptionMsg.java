package cn.swordsmen.exception.custom;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * 异常信息封装
 *
 * @author caiwanghong
 * @date 2023/8/25 14:14
 * @version 1.0
 */
@Data
@Builder
public class ExceptionMsg {
    @Singular
    private final List<String> errors;

}
