package cc.abing.abstart.suite.system.security;

/**
 * 登录防爆破存储（用户名+IP 维度）
 * <p>语义：连续失败达到阈值后锁定一段时间，锁定到期后自动解锁并重新计数。
 * 当前提供内存实现（{@code InMemoryLoginAttemptStore}），
 * 预留 Redis 实现（INCR + EXPIRE）以支持多实例/分布式部署。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public interface LoginAttemptStore {

    /**
     * 指定 key（如 username:ip）当前是否处于锁定状态
     */
    boolean isLocked(String key);

    /**
     * 记录一次失败；内部计数并判断是否达到阈值触发锁定
     */
    void recordFailure(String key);

    /**
     * 清除计数（登录成功时调用）
     */
    void clear(String key);
}
