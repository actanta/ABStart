package cc.abing.abstart.api.interceptor;

import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录校验拦截器
 * <p>对除认证相关接口（{@code /api/v1/auth/**}）之外的所有接口校验登录态：
 * 未登录（token 缺失/无效/过期）抛出 {@link BizException}(UNAUTHORIZED)，
 * 由全局异常处理器统一返回 code=401 的 JSON 响应。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!StpUtil.isLogin()) {
            throw new BizException(CodeMsg.UNAUTHORIZED, "请先登录");
        }
        return true;
    }
}
