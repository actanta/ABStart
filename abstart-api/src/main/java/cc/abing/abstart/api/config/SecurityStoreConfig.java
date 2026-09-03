package cc.abing.abstart.api.config;

import cc.abing.abstart.suite.system.security.LoginAttemptStore;
import cc.abing.abstart.suite.system.security.NonceStore;
import cc.abing.abstart.suite.system.security.impl.InMemoryLoginAttemptStore;
import cc.abing.abstart.suite.system.security.impl.InMemoryNonceStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全存储装配（防重放 nonce / 登录防爆破）
 * <p>默认使用内存实现。多实例/分布式部署时：新增 Redis 版 NonceStore / LoginAttemptStore
 * （SETNX+EXPIRE、INCR+EXPIRE），并在此处用
 * {@code @ConditionalOnProperty(name = "abstart.security.store-type", havingValue = "redis")}
 * 切换为 Redis Bean 即可，业务代码无需改动。</p>
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
}
