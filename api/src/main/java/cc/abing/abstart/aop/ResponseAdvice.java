package cc.abing.abstart.aop;

import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.support.system.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ABing
 * @since 2022/10/17
 */
@RestControllerAdvice(basePackages = SystemConstant.PACKAGE_PREFIX + "api")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 返回 true 则下面 beforeBodyWrite方法被调用, 否则就不调用下述方法
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result || body instanceof String) {
            return body;
        }
        return Result.success(body);
    }
}
