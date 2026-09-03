package cc.abing.abstart.api.util;

import cc.abing.abstart.suite.system.constant.SystemConstant;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 客户端 IP 解析工具
 *
 * @author ABing
 * @since 2026-09-03
 */
public final class IpUtil {

    private IpUtil() {
    }

    /**
     * 获取请求方真实IP地址
     * @param request 当前请求
     * @return 客户端真实IP，解析不到时返回空串
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !SystemConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            return ip.split(",")[0];
        }

        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !SystemConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
