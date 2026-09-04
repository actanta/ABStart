package cc.abing.abstart.api.config;

import cc.abing.abstart.suite.system.security.LoginAttemptStore;
import cc.abing.abstart.suite.system.security.NonceStore;
import cc.abing.abstart.suite.system.security.RateLimitStore;
import cc.abing.abstart.suite.system.security.impl.InMemoryLoginAttemptStore;
import cc.abing.abstart.suite.system.security.impl.InMemoryNonceStore;
import cc.abing.abstart.suite.system.security.impl.InMemoryRateLimitStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全存储装配（防重放 nonce / 登录防爆破 / 接口限流）
 * <p>默认使用内存实现（限流基于 Guava RateLimiter 令牌桶）。
 * 多实例/分布式部署时：新增 Redis 版 NonceStore / LoginAttemptStore / RateLimitStore
 * （SETNX+EXPIRE、INCR+EXPIRE、Redisson RRateLimiter），并在此处用
 * {@code @ConditionalOnProperty(name = "abstart.security.store-type", havingValue = "redis")}
 * 切换为 Redis Bean 即可，业务代码无需改动。
 * 接入 Redis 限流时，建议以包装 Bean 实现「Redis 优先、故障回退 local」的降级
 * （对应配置 abstart.security.rate-limit.fallback-to-local=true），可用性优先于限流的分布式精确性。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
@Configuration
public class SecurityStoreConfig {

    @Bean
    public NonceStore nonceStore() {
        return new InMemoryNonceStore();
    }

    @Bean
    public LoginAttemptStore loginAttemptStore(
            @Value("${abstart.security.brute-force.max-attempts:5}") int maxAttempts,
            @Value("${abstart.security.brute-force.lock-seconds:300}") long lockSeconds) {
        return new InMemoryLoginAttemptStore(maxAttempts, lockSeconds * 1000L);
    }

    @Bean
    public RateLimitStore rateLimitStore(
            @Value("${abstart.security.rate-limit.max-size:10000}") int maxSize,
            @Value("${abstart.security.rate-limit.idle-seconds:600}") long idleSeconds) {
        return new InMemoryRateLimitStore(maxSize, idleSeconds * 1000L);
    }
}
