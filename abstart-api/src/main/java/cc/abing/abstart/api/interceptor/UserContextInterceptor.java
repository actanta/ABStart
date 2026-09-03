package cc.abing.abstart.api.interceptor;

import cc.abing.abstart.api.util.IpUtil;
import cc.abing.abstart.biz.context.UserContext;
import cc.abing.abstart.biz.context.UserContextHolder;
import cc.abing.abstart.suite.system.constant.SystemConstant;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

/**
 * 用户请求上下文拦截器
 * <p>在请求进入 Controller 前，从请求头与 sa-token 会话中解析用户/客户端信息，
 * 组装 {@link UserContext} 存入当前请求的 Request 域，业务层经 {@link UserContextHolder} 读取。
 * 后续需要新增请求信息点（如更多业务请求头）时，只需扩展本类组装逻辑并补充 {@link UserContext} 字段。</p>
 *
 * @author ABing
 * @since 2026-08-29
 */
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UserContext context = new UserContext();
        context.setIp(IpUtil.getIpAddr(request));
        context.setVersion(request.getHeader(SystemConstant.HEADER_VERSION));
        context.setDeviceId(request.getHeader(SystemConstant.HEADER_DEVICE_ID));
        // sa-token：当前请求携带的令牌与会话登录标识，未登录场景下为空
        context.setToken(StpUtil.getTokenValue());
        Object loginId = StpUtil.getLoginIdDefaultNull();
        context.setUserId(loginId == null ? null : String.valueOf(loginId));
        // 其余业务请求头原样保留，供未建模的自定义请求头扩展读取
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                context.putHeader(name, request.getHeader(name));
            }
        }
        UserContextHolder.set(context);
        return true;
    }
}
