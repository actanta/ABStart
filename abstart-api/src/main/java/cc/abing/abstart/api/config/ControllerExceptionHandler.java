package cc.abing.abstart.api.config;

import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.result.Result;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ABing
 * @since 2026-08-25
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class ControllerExceptionHandler {

    //org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'PUT' not supported
    //org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
    //缺少请求体org.springframework.http.converter.HttpMessageNotReadableException: Required request body is missing: public java.lang.String cc.abing.abstart.api.controller.ExampleController.ok(java.util.Map)
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e) {
        return logException("系统异常:" + e.getClass().getSimpleName(), e);
    }

    @ExceptionHandler({BizException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object handleBusinessException(BizException e) {
        return logException("业务异常:" + e.getCode() + ":" + e.getInfo(), e);
    }

    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MismatchedInputException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleClientException(HttpMessageNotReadableException e) {
        return log("请求传参异常:" + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验异常:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        log.warn("请求地址:{}, 参数校验异常:{}", request.getRequestURI(), sb);
        return log(sb.toString());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String validateMsg = String.valueOf(
                constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        log.warn("请求地址:{}, 参数校验异常:{}", request.getRequestURI(), validateMsg);
        if (StringUtils.hasText(validateMsg)) {
            return log(validateMsg);
        }
        return Result.failed();
    }

    private Result logException(String exceptionInfo, Exception e) {
        log.warn(exceptionInfo, e);
        return Result.failed(exceptionInfo);
    }

    private Result log(String exceptionInfo) {
        log.info(exceptionInfo);
        return Result.failed(exceptionInfo);
    }

}
