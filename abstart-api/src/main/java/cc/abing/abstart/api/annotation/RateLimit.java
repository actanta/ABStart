package cc.abing.abstart.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流注解（令牌桶，按来源维度计数）
 * <p>标注在 Controller 方法上，表示该接口在全局基础限流之上再按给定速率单独加严：
 * 同一来源（默认 IP）下，不同接口的注解档相互独立、互不挤占；注解档通常比全局档更紧，会先被触发。</p>
 *
 * @author ABing
 * @since 2026-09-03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 资源标识，默认空串：自动取 {@code 类简单名.方法名}；
     * 建议显式指定，便于多方法共享同一档位或语义化命名（如 {@code sendCode}）
     */
    String key() default "";

    /**
     * 每秒补充令牌数（qps），如 {@code 0.2} 表示每 5 秒放行 1 次。
     * 必须显式给出；小于等于 0 视为不限流（防御配置错误）
     */
    double rate();
}
