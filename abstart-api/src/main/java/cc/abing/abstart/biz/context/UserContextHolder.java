package cc.abing.abstart.biz.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.function.Function;

/**
 * 用户请求上下文门面（基于 Request 域）
 * <p>通过 {@link RequestContextHolder} 存取当前请求的 {@link UserContext}，
 * 生命周期由 Servlet Request 域托管，请求结束自动释放，无线程池串号/泄漏风险。
 * 业务层无需感知 Servlet API，直接调用便捷方法即可。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public final class UserContextHolder {

    /**
     * UserContext 在 Request 域中的存储 key
     */
    private static final String CONTEXT_ATTRIBUTE = UserContextHolder.class.getName();

    private UserContextHolder() {
    }

    /**
     * 将用户请求上下文写入当前请求的 Request 域（由拦截器调用）
     */
    public static void set(UserContext context) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(CONTEXT_ATTRIBUTE, context, RequestAttributes.SCOPE_REQUEST);
        }
    }

    /**
     * 获取当前请求的用户请求上下文
     *
     * @return UserContext；无请求上下文或未写入时返回 null
     */
    public static UserContext get() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return (UserContext) requestAttributes.getAttribute(CONTEXT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 清除当前请求的用户请求上下文
     * <p>由拦截器在 afterCompletion 阶段调用。Request 域下上下文随请求结束自动释放，
     * 显式清除主要为后续切换为 ThreadLocal 存储（如非 HTTP 调用场景）时保证上下文被正确清空。</p>
     */
    public static void clear() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.removeAttribute(CONTEXT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        }
    }

    /**
     * 便捷读取：客户端 IP
     */
    public static String getIp() {
        return value(UserContext::getIp);
    }

    /**
     * 便捷读取：当前登录用户 ID
     */
    public static String getUserId() {
        return value(UserContext::getUserId);
    }

    /**
     * 便捷读取：当前请求携带的访问令牌
     */
    public static String getToken() {
        return value(UserContext::getToken);
    }

    /**
     * 便捷读取：客户端版本
     */
    public static String getVersion() {
        return value(UserContext::getVersion);
    }

    /**
     * 便捷读取：客户端设备标识
     */
    public static String getDeviceId() {
        return value(UserContext::getDeviceId);
    }

    /**
     * 便捷读取：原始请求头（大小写不敏感），未建模的自定义头可经此扩展读取
     */
    public static String getHeader(String name) {
        UserContext context = get();
        return context == null ? null : context.getHeader(name);
    }

    /**
     * 从当前上下文取值，无上下文时返回 null，避免业务层反复判空
     */
    private static String value(Function<UserContext, String> getter) {
        UserContext context = get();
        return context == null ? null : getter.apply(context);
    }
}
