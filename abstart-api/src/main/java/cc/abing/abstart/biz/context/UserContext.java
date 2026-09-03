package cc.abing.abstart.biz.context;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * 用户请求上下文：一次请求内与当前操作用户/客户端相关的信息快照
 * <p>由 {@code UserContextInterceptor} 在 preHandle 阶段组装并存入 Request 域，
 * 业务层通过 {@link UserContextHolder} 读取。后续需要新增请求信息点时，
 * 直接为该类补充字段并在拦截器中赋值即可，无需新增上下文类。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public class UserContext {

    /**
     * 客户端真实 IP
     */
    private String ip;

    /**
     * 当前登录用户 ID
     */
    private String userId;

    /**
     * 当前请求携带的访问令牌
     */
    private String token;

    /**
     * 客户端时间戳（请求头 X-Timestamp）
     */
    private String timestamp;

    /**
     * 客户端版本（请求头 X-Version）
     */
    private String version;

    /**
     * 客户端设备标识（请求头 X-Device-Id）
     */
    private String deviceId;

    /**
     * 其余业务请求头兜底（key大小写不敏感），供未建模的自定义请求头扩展读取
     */
    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 存放一个请求头
     */
    public void putHeader(String name, String value) {
        if (name != null) {
            headers.put(name, value);
        }
    }

    /**
     * 按名称读取原始请求头（大小写不敏感），不存在返回 null
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * 全部原始请求头的只读视图
     */
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}
