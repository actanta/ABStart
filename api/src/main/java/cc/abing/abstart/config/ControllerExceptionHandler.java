package cc.abing.abstart.config;

import cc.abing.abstart.support.system.exception.ABException;
import cc.abing.abstart.support.system.exception.ABParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author ABing
 * @since 2022/8/6
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public Object exception(Exception e) {
        return logException("系统异常:"+e.getMessage(),e);
    }


    @ExceptionHandler({ABException.class, ABParamException.class})
    public Object businessException(ABException e) {
        return logException("业务异常:"+e.getInfo(),e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object paramException(MissingServletRequestParameterException e) {
        return log("缺少参数:"+e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object paramException(MethodArgumentTypeMismatchException e) {
        return log("参数错误:"+e.getMessage());
    }

    private String logException(String exceptionInfo,Exception e){
        log.info(exceptionInfo,e);
        return exceptionInfo;
    }

    private String log(String exceptionInfo){
        log.info(exceptionInfo);
        return exceptionInfo;
    }
}
