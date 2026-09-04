package cc.abing.abstart.api.interceptor;

import cc.abing.abstart.api.annotation.RateLimit;
import cc.abing.abstart.biz.context.UserContextHolder;
import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import cc.abing.abstart.suite.system.security.RateLimitStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 接口限流拦截器（令牌桶）
 * <p>需挂在 UserContextInterceptor 之后执行（复用其已组装的客户端 IP），两级计数：
 * 1) 全局基础档：所有请求按「每 IP 每秒 default-qps」限流（未登录接口若先被登录校验拦截则不计入）；
 * 2) 注解加严档：标注了 {@link RateLimit} 的方法再按注解 rate 单独计数，同一 IP 下各接口互不挤占。
 * CORS 预检（OPTIONS）放行；IP 解析失败以 {@code unknown} 维度兜底；超限抛 429（RATE_LIMITED）。
 * Redis 存储接入后，Redis 故障时的本地降级由 SecurityStoreConfig 的装配逻辑负责，本拦截器无感。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    /**
     * IP 解析失败时的兜底维度
     */
    private static final String UNKNOWN_IP = "unknown";

    /**
     * 全局基础档 key 前缀
     */
    private static final String GLOBAL_PREFIX = "G:";

    /**
     * 注解加严档 key 前缀
     */
    private static final String ANNOTATION_PREFIX = "A:";

    private final RateLimitStore rateLimitStore;

    /**
     * 限流总开关
     */
    @Value("${abstart.security.rate-limit.enabled:true}")
    private boolean enabled;

    /**
     * 全局基础档：每 IP 每秒放行请求数（0=关闭基础档，仅注解生效）
     */
    @Value("${abstart.security.rate-limit.default-qps:2}")
    private double defaultQps;

    public RateLimitInterceptor(RateLimitStore rateLimitStore) {
        this.rateLimitStore = rateLimitStore;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!enabled || HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String ip = UserContextHolder.getIp();
        String client = (ip == null || ip.trim().isEmpty()) ? UNKNOWN_IP : ip.trim();
        // 注解加严档：先于全局档检查（注解速率通常更紧，先触发超限）
        if (handler instanceof HandlerMethod handlerMethod) {
            RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
            if (rateLimit != null) {
                String resource = rateLimit.key().trim().isEmpty()
                        ? handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName()
                        : rateLimit.key().trim();
                if (!rateLimitStore.tryAcquire(ANNOTATION_PREFIX + client + ":" + resource, rateLimit.rate())) {
                    throw new BizException(CodeMsg.RATE_LIMITED);
                }
            }
        }
        // 全局基础档：default-qps 非正视为关闭
        if (defaultQps > 0 && !rateLimitStore.tryAcquire(GLOBAL_PREFIX + client, defaultQps)) {
            throw new BizException(CodeMsg.RATE_LIMITED);
        }
        return true;
    }
}
