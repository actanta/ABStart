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
	 * 系统异常
	 */
	SYSTEM_ERR("500", "系统异常"),

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