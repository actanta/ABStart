package cc.abing.abstart.suite.system.security;

/**
 * nonce（客户端随机串）防重放存储
 * <p>语义：同一 nonce 仅在有效期内允许出现一次，重复出现视为重放请求。
 * 当前提供内存实现（{@code InMemoryNonceStore}），
 * 预留 Redis 实现（SETNX + EXPIRE）以支持多实例/分布式部署。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public interface NonceStore {

    /**
     * 若 nonce 不存在则记录并返回 true；有效期内已存在返回 false（视为重放）。
     * 已过期的残留记录可被重新占用。
     *
     * @param nonce     客户端随机串
     * @param ttlMillis 有效时长（毫秒）
     * @return true=首次接收；false=窗口内重复
     */
    boolean addIfAbsent(String nonce, long ttlMillis);

    /**
     * 清理已过期的记录（供定时任务或容量触发时调用）
     */
    void clearExpired();
}
