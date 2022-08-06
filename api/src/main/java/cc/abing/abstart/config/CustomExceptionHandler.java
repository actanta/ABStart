package cc.abing.abstart.config;

import cc.abing.abstart.support.system.error.ABException;
import cc.abing.abstart.support.system.error.ABParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
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
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        log.info("系统异常:"+e.getMessage());
        return ResponseEntity.badRequest().body("系统异常:"+e.getMessage());
    }


    @ExceptionHandler({ABException.class, ABParamException.class})
    public ResponseEntity<Object> businessException(ABException e) {
        log.info("业务异常:"+e.getInfo());
        return ResponseEntity.badRequest().body("业务异常:"+e.getInfo());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> paramException(MissingServletRequestParameterException e) {
        log.info("缺少参数:"+e.getMessage());
        return ResponseEntity.badRequest().body("缺少参数:"+e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> paramException(MethodArgumentTypeMismatchException e) {
        log.info("参数错误:"+e.getMessage());
        return ResponseEntity.badRequest().body("参数错误:"+e.getMessage());
    }
}
