package cc.abing.abstart.api.config;

import cc.abing.abstart.api.interceptor.AuthInterceptor;
import cc.abing.abstart.api.interceptor.ReplayProtectInterceptor;
import cc.abing.abstart.api.interceptor.UserContextInterceptor;
import cc.abing.abstart.suite.system.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ReplayProtectInterceptor replayProtectInterceptor;

    private final AuthInterceptor authInterceptor;

    private final UserContextInterceptor userContextInterceptor;

    @Autowired
    public WebConfig(ReplayProtectInterceptor replayProtectInterceptor, AuthInterceptor authInterceptor,
            UserContextInterceptor userContextInterceptor) {
        this.replayProtectInterceptor = replayProtectInterceptor;
        this.authInterceptor = authInterceptor;
        this.userContextInterceptor = userContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 防重放：全站所有请求强制（含认证接口），最先执行
        registry.addInterceptor(replayProtectInterceptor)
                .addPathPatterns(SystemConstant.BASE_PATH + "/**");
        // 登录校验：除认证相关接口外，所有接口均需登录
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(SystemConstant.BASE_PATH + "/**")
                .excludePathPatterns(SystemConstant.BASE_PATH + "/auth/**");
        // 用户请求上下文组装
        registry.addInterceptor(userContextInterceptor)
                .addPathPatterns(SystemConstant.BASE_PATH + "/**");
    }
}
