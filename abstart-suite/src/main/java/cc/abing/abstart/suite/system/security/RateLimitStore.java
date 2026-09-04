package cc.abing.abstart.suite.system.security;

/**
 * 接口限流存储（令牌桶语义）
 * <p>语义：{@link #tryAcquire(String, double)} 以「每秒补充令牌数」为速率参数，
 * 按 key（通常为 {@code 来源维度:资源标识}，如 {@code IP:接口}）维度独立限流，
 * 允许合理突发后平滑限制，返回 false 表示已超限。</p>
 * <p>当前提供内存实现（{@code InMemoryRateLimitStore}，基于 Guava RateLimiter），
 * 预留 Redis 实现（Redisson RRateLimiter）以支持多实例/分布式部署：
 * 速率参数与 RRateLimiter 的「每秒补充 rate 个令牌」语义 1:1 对应，可直接迁移。
 * Redis 故障时的本地降级兜底由存储装配层（SecurityStoreConfig 的包装 Bean）负责，
 * 本接口不感知降级细节。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
public interface RateLimitStore {

    /**
     * 尝试获取 1 个令牌（瞬时判断，不阻塞等待）。
     *
     * @param key           限流维度 key，由调用方拼接（如 {@code G:ip}、{@code A:ip:接口标识}）
     * @param ratePerSecond 令牌桶每秒补充速率；小于等于 0 视为不限流，直接放行（防御配置错误）
     * @return true=放行；false=已超限
     */
    boolean tryAcquire(String key, double ratePerSecond);

    /**
     * 清理闲置超过给定时间的 key（内存实现据此避免无限膨胀，
     * 供后台清理线程或容量兜底时调用；Redis 实现可依赖服务端自动回收而空实现）
     *
     * @param idleMillis 闲置判定阈值（毫秒）
     */
    void clearIdle(long idleMillis);
}
