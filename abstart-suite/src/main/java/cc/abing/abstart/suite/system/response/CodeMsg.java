package cc.abing.abstart.suite.system.response;

public enum CodeMsg implements ICodeMsg {

	/**
	 * 一切正常
	 */
	SYSTEM_OK("200", "请求成功"),

	/**
	 * 错误请求
	 */
	BAD_REQUEST("400", "错误请求"),

	/**
	 * 未认证
	 */
	UNAUTHORIZED("401", "未认证"),

	/**
	 * 无权访问
	 */
	FORBIDDEN("403", "无权访问"),

	/**
	 * 访问资源不存在
	 */
	NOT_FOUND("404", "访问资源不存在"),

	/**
	 * 系统异常
	 */
	SYSTEM_ERR("500", "系统异常"),

	/**
	 * 参数缺失
	 */
	PARAM_REQUIRE("400", "参数缺失"),

	/**
	 * 请求已过期（防重放时间窗超限）
	 */
	REQUEST_EXPIRED("408", "请求已过期"),

	/**
	 * 重复请求（防重放 nonce 已使用）
	 */
	REPEATED_REQUEST("409", "重复请求"),

	/**
	 * 尝试次数过多（登录防爆破锁定）
	 */
	TOO_MANY_ATTEMPTS("429", "尝试次数过多，请稍后再试"),

	/**
	 * 请求过于频繁（接口限流触发）
	 */
	RATE_LIMITED("429", "请求过于频繁，请稍后再试"),

	;

	private final String code;

	private final String msg;

	CodeMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

}