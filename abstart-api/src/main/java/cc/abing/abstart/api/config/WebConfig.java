package cc.abing.abstart.api.config;

import cc.abing.abstart.api.interceptor.UserContextInterceptor;
import cc.abing.abstart.suite.system.constant.SystemConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 *
 * @author ABing
 * @since 2026-08-29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor())
                .addPathPatterns(SystemConstant.BASE_PATH + "/**");
    }
}
