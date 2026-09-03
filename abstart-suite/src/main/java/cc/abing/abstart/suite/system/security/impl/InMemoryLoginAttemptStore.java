package cc.abing.abstart.suite.system.security.impl;

import cc.abing.abstart.suite.system.security.LoginAttemptStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Java 自带集合（ConcurrentHashMap）的登录防爆破存储
 * <p>策略：{@code maxAttempts} 次连续失败后锁定 {@code lockMillis}；
 * 锁定到期后解锁；超过 {@code lockMillis} 无新失败则自动清空历史计数（避免长期累积误锁）。
 * 后台守护线程定时清理。多实例部署时应替换为 Redis 实现。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public class InMemoryLoginAttemptStore implements LoginAttemptStore {

    /**
     * 后台清理间隔（秒）
     */
    private static final long CLEAN_INTERVAL_SECONDS = 60L;

    private final int maxAttempts;

    private final long lockMillis;

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    private final ScheduledExecutorService cleaner;

    public InMemoryLoginAttemptStore(int maxAttempts, long lockMillis) {
        this.maxAttempts = maxAttempts;
        this.lockMillis = lockMillis;
        this.cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "login-attempt-store-cleaner");
            thread.setDaemon(true);
            return thread;
        });
        this.cleaner.scheduleWithFixedDelay(this::clearIdle, CLEAN_INTERVAL_SECONDS, CLEAN_INTERVAL_SECONDS,
                TimeUnit.SECONDS);
    }

    @Override
    public boolean isLocked(String key) {
        Attempt attempt = attempts.get(key);
        return attempt != null && attempt.lockUntilMs > System.currentTimeMillis();
    }

    @Override
    public void recordFailure(String key) {
        attempts.compute(key, (k, attempt) -> {
            long now = System.currentTimeMillis();
            if (attempt == null) {
                attempt = new Attempt();
            }
            if (attempt.lockUntilMs > now) {
                // 已处于锁定期，无需累计
                return attempt;
            } else {
                // 不在锁定期内，确认是否重置计数，累计计数
                resetIfNeeded(attempt, now);
                attempt.failures++;
                attempt.lastFailureMs = now;
                if (attempt.failures >= maxAttempts) {
                    attempt.lockUntilMs = now + lockMillis;
                }
                return attempt;
            }
        });
    }

    @Override
    public void clear(String key) {
        attempts.remove(key);
    }

    private void resetIfNeeded(Attempt attempt, long now){
        // 很久没有失败行为（距离上次失败间隔lockMillis锁定时间），空闲超时重置
        if (attempt.lastFailureMs != 0 && now - attempt.lastFailureMs > lockMillis) {
            attempt.failures = 0;
            attempt.lockUntilMs = 0;
        }
    }

    /**
     * 清理长时间无活动的计数记录
     */
    private void clearIdle() {
        long now = System.currentTimeMillis();
        attempts.entrySet().removeIf(entry -> {
            Attempt attempt = entry.getValue();
            boolean locked = attempt.lockUntilMs > now;
            boolean idle = now - attempt.lastFailureMs > lockMillis;
            return !locked && idle;
        });
    }

    /**
     * 单个 key 的失败计数与锁定状态
     */
    private static class Attempt {
        private int failures;
        private long lockUntilMs;
        private long lastFailureMs;
    }
}
