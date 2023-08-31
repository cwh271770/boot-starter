package cn.swordsman.exception.handler;

import cn.hutool.core.util.StrUtil;
import cn.swordsman.exception.custom.ArgumentException;
import cn.swordsman.exception.custom.BusinessException;
import cn.swordsman.exception.custom.ExceptionMsg;
import cn.swordsman.response.RespResult;
import cn.swordsman.response.RespStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Arrays;

/**
 * 全局异常处理
 *
 * @author caiwanghong
 * @date 2023/8/25 16:45
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
        log.info(">>>>>>>>>>>>>GlobalExceptionHandler init<<<<<<<<<<<<<");
    }


    /**
     * request异常处理
     *
     * @author caiwanghong
     * @param e
     * @date 2023/8/25 16:45
     * @return RespResult<ExceptionMsg>
     */
    @ResponseBody
    @ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class, ValidationException.class})
    public RespResult<ExceptionMsg> handleRequestException(@NonNull Exception e) {
        log.error("RequestException: {}", ExceptionUtils.getStackTrace(e));
        ExceptionMsg.ExceptionMsgBuilder exceptionMsgBuilder = ExceptionMsg.builder();
        if (e instanceof MethodArgumentNotValidException) {
            // 处理json请求体调用接口对象参数校验失败
            ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().stream()
                .map(fieldError -> StrUtil.join(": ", fieldError.getField(), fieldError.getDefaultMessage()))
                .forEach(exceptionMsgBuilder::error);
        } else if (e instanceof BindException) {
            // 处理form-data表单方式调用接口对象参数校验失败
            ((BindException) e).getBindingResult().getFieldErrors().stream()
                .map(fieldError -> StrUtil.join(": ", fieldError.getField(), fieldError.getDefaultMessage()))
                .forEach(exceptionMsgBuilder::error);
        } else if (e instanceof ConstraintViolationException) {
            // 处理单个参数校验失败
            ((ConstraintViolationException) e).getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .forEach(exceptionMsgBuilder::error);
        } else {
            // 其他
            if (e.getMessage() != null) {
                exceptionMsgBuilder.error(e.getMessage());
            }
        }

        return RespResult.fail(exceptionMsgBuilder.build(), RespStatus.HTTP_STATUS_400);
    }

    /**
     * 自定义请求参数异常
     *
     * @author caiwanghong
     * @param argumentException
     * @date 2023/8/25 16:45
     * @return RespResult<?>
     */
    @ResponseBody
    @ExceptionHandler(ArgumentException.class)
    public RespResult<?> handleArgumentException(ArgumentException argumentException) {
        log.error("ArgumentException: {}", ExceptionUtils.getStackTrace(argumentException));
        return RespResult.fail(ExceptionMsg.builder().error(argumentException.getLocalizedMessage()).build(),
            "Invalid Parameter", argumentException.getCode());
    }

    /**
     * 自定义业务异常处理
     *
     * @author caiwanghong
     * @param businessException
     * @date 2023/8/25 16:45
     * @return RespResult<?>
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public RespResult<?> handleBusinessException(BusinessException businessException) {
        log.error("BusinessException: {}", ExceptionUtils.getStackTrace(businessException));
        ExceptionMsg.ExceptionMsgBuilder exceptionMsgBuilder = ExceptionMsg.builder();
        Arrays.stream(businessException.getStackTrace()).limit(3)
            .map(StackTraceElement::toString)
            .forEach(exceptionMsgBuilder::error);
        return RespResult.fail(exceptionMsgBuilder.build(), businessException.getLocalizedMessage(),
            businessException.getCode());
    }

    /**
     * 其他异常处理
     *
     * @author caiwanghong
     * @param exception
     * @date 2023/8/25 16:45
     * @return RespResult<?>
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RespResult<?> handleException(Exception exception) {
        log.error("Exception: {}", ExceptionUtils.getStackTrace(exception));
        ExceptionMsg.ExceptionMsgBuilder exceptionMsgBuilder = ExceptionMsg.builder();
        Arrays.stream(exception.getStackTrace()).limit(3)
            .map(StackTraceElement::toString)
            .forEach(exceptionMsgBuilder::error);
        return RespResult.fail(exceptionMsgBuilder.build(), exception.getLocalizedMessage());
    }

}
