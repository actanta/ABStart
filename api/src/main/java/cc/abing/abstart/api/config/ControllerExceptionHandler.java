package cc.abing.abstart.api.config;

import cc.abing.abstart.support.system.exception.BizException;
import cc.abing.abstart.support.system.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ABing
 * @since 2022/8/6
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler
	public Object handleException(Exception e) {
		return logException("系统异常:" + e.getMessage(), e);
	}

	@ExceptionHandler({ BizException.class })
	public Object handleBusinessException(BizException e) {
		return logException("业务异常:" + e.getInfo(), e);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		return log("缺少参数:" + e.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		return log("参数错误:" + e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		StringBuilder sb = new StringBuilder("请求地址:" + request.getRequestURI() + ", 参数校验异常:");
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
		}
		return log(sb.toString());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
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
		// TODO 生产环境应直接返回Result.failed()
		return Result.failed(exceptionInfo);
	}

	private Result log(String exceptionInfo) {
		log.info(exceptionInfo);
		// TODO 生产环境应直接返回Result.failed()
		return Result.failed(exceptionInfo);
	}

}
