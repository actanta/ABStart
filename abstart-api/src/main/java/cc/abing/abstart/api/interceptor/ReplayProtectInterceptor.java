package cc.abing.abstart.api.interceptor;

import cc.abing.abstart.suite.system.constant.SystemConstant;
import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import cc.abing.abstart.suite.system.security.NonceStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 防重放拦截器（全站强制，含认证接口）
 * <p>所有请求需携带 {@code X-Timestamp}（13 位毫秒时间戳）与 {@code X-Nonce}（随机串）：
 * 1) 时间戳与服务器时间差超过时间窗 → 408 请求已过期；
 * 2) 同一 nonce 在窗口内重复出现 → 409 重复请求。
 * CORS 预检请求（OPTIONS）放行。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
@Component
public class ReplayProtectInterceptor implements HandlerInterceptor {

    private final NonceStore nonceStore;

    /**
     * 时间窗（毫秒），默认 ±5 分钟；nonce 保留期为该值的 2 倍
     */
    @Value("${abstart.security.replay.window-millis:300000}")
    private long windowMillis;

    public ReplayProtectInterceptor(NonceStore nonceStore) {
        this.nonceStore = nonceStore;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        long clientTime = parseTimestamp(request.getHeader(SystemConstant.HEADER_TIMESTAMP));
        String nonce = request.getHeader(SystemConstant.HEADER_NONCE);
        if (nonce == null || nonce.trim().isEmpty()) {
            throw new BizException(CodeMsg.BAD_REQUEST, "缺少请求头 " + SystemConstant.HEADER_NONCE);
        }
        if (Math.abs(System.currentTimeMillis() - clientTime) > windowMillis) {
            throw new BizException(CodeMsg.REQUEST_EXPIRED);
        }
        if (!nonceStore.addIfAbsent(nonce, windowMillis * 2)) {
            throw new BizException(CodeMsg.REPEATED_REQUEST);
        }
        return true;
    }

    /**
     * 解析毫秒时间戳请求头，缺失/非法均拒绝
     */
    private long parseTimestamp(String timestampHeader) {
        if (timestampHeader == null || timestampHeader.trim().isEmpty()) {
            throw new BizException(CodeMsg.BAD_REQUEST, "缺少请求头 " + SystemConstant.HEADER_TIMESTAMP);
        }
        try {
            return Long.parseLong(timestampHeader.trim());
        } catch (NumberFormatException e) {
            throw new BizException(CodeMsg.BAD_REQUEST,
                    SystemConstant.HEADER_TIMESTAMP + " 格式错误，需为 13 位毫秒时间戳");
        }
    }
}
