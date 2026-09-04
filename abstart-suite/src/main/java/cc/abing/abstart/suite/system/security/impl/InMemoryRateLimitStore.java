package cc.abing.abstart.suite.system.security.impl;

import cc.abing.abstart.suite.system.security.RateLimitStore;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Guava RateLimiter（令牌桶，SmoothBursty）的限流存储
 * <p>每 key 持有独立限流器：Guava 默认允许 1 秒量级的突发后按速率平滑，贴合真实用户流量。
 * 后台守护线程定期清理闲置 key；{@link #maxSize} 触发容量兜底清理，防止内存膨胀。
 * 多实例部署时应替换为 Redis（Redisson RRateLimiter）实现。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public class InMemoryRateLimitStore implements RateLimitStore {

    /**
     * 默认最大 key 数
     */
    private static final int DEFAULT_MAX_SIZE = 10_000;

    /**
     * 默认闲置清理阈值（毫秒）
     */
    private static final long DEFAULT_IDLE_MILLIS = 10 * 60 * 1000L;

    /**
     * 后台清理间隔（秒）
     */
    private static final long CLEAN_INTERVAL_SECONDS = 60L;

    /**
     * key -> 限流器条目（含最近访问时间，供闲置清理判定）
     */
    private final Map<String, Entry> limiters = new ConcurrentHashMap<>();

    private final int maxSize;

    private final long idleMillis;

    private final ScheduledExecutorService cleaner;

    public InMemoryRateLimitStore() {
        this(DEFAULT_MAX_SIZE, DEFAULT_IDLE_MILLIS);
    }

    public InMemoryRateLimitStore(int maxSize, long idleMillis) {
        this.maxSize = maxSize;
        this.idleMillis = idleMillis;
        this.cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "rate-limit-store-cleaner");
            thread.setDaemon(true);
            return thread;
        });
        this.cleaner.scheduleWithFixedDelay(() -> clearIdle(idleMillis), CLEAN_INTERVAL_SECONDS,
                CLEAN_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public boolean tryAcquire(String key, double ratePerSecond) {
        // 防御配置错误：非正速率视为不限流
        if (ratePerSecond <= 0) {
            return true;
        }
        Entry entry = limiters.compute(key, (k, existing) -> {
            Entry target = existing;
            if (target == null) {
                target = new Entry(RateLimiter.create(ratePerSecond));
            } else if (target.rateLimiter.getRate() != ratePerSecond) {
                // 速率随配置变化时动态调整，无需重建限流器
                target.rateLimiter.setRate(ratePerSecond);
            }
            target.lastAccessMs = System.currentTimeMillis();
            return target;
        });
        // 容量兜底：超限时先清理闲置记录再放行，尽力避免内存膨胀
        if (limiters.size() > maxSize) {
            clearIdle(idleMillis);
        }
        return entry.rateLimiter.tryAcquire();
    }

    @Override
    public void clearIdle(long idleMillis) {
        long threshold = System.currentTimeMillis() - idleMillis;
        limiters.entrySet().removeIf(entry -> entry.getValue().lastAccessMs < threshold);
    }

    /**
     * 限流器条目：限流器 + 最近访问时间（毫秒）
     */
    private static final class Entry {

        private final RateLimiter rateLimiter;

        private volatile long lastAccessMs;

        private Entry(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            this.lastAccessMs = System.currentTimeMillis();
        }
    }
}
