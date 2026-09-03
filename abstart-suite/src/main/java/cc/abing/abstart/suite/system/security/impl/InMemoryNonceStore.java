package cc.abing.abstart.suite.system.security.impl;

import cc.abing.abstart.suite.system.security.NonceStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Java 自带集合（ConcurrentHashMap）的 nonce 防重放存储
 * <p>后台守护线程定时清理过期记录；{@link #maxSize} 触发容量兜底清理，防止内存膨胀。
 * 多实例部署时应替换为 Redis 实现。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public class InMemoryNonceStore implements NonceStore {

    /**
     * 默认最大记录数
     */
    private static final long DEFAULT_MAX_SIZE = 100_000L;

    /**
     * 后台清理间隔（秒）
     */
    private static final long CLEAN_INTERVAL_SECONDS = 60L;

    /**
     * nonce -> 过期时间戳（毫秒）
     */
    private final Map<String, Long> nonces = new ConcurrentHashMap<>();

    private final long maxSize;

    private final ScheduledExecutorService cleaner;

    public InMemoryNonceStore() {
        this(DEFAULT_MAX_SIZE);
    }

    public InMemoryNonceStore(long maxSize) {
        this.maxSize = maxSize;
        this.cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "nonce-store-cleaner");
            thread.setDaemon(true);
            return thread;
        });
        this.cleaner.scheduleWithFixedDelay(this::clearExpired, CLEAN_INTERVAL_SECONDS, CLEAN_INTERVAL_SECONDS,
                TimeUnit.SECONDS);
    }

    @Override
    public boolean addIfAbsent(String nonce, long ttlMillis) {
        long expireAt = System.currentTimeMillis() + ttlMillis;
        Long previous = nonces.putIfAbsent(nonce, expireAt);
        if (previous == null) {
            // 容量兜底：超限时先清理过期记录再继续
            if (nonces.size() > maxSize) {
                clearExpired();
            }
            return true;
        }
        // 已存在：若为过期残留（极端情况，清理线程尚未执行）则允许重新占用
        if (previous <= System.currentTimeMillis()) {
            return nonces.replace(nonce, previous, expireAt);
        }
        return false;
    }

    @Override
    public void clearExpired() {
        long now = System.currentTimeMillis();
        nonces.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}
