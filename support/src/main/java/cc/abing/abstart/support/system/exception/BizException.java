package cc.abing.abstart.support.system.exception;

import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.support.system.response.ICodeMsg;
import cc.abing.abstart.support.system.response.CodeMsg;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = -8551154617416340632L;

	/**
	 * 异常码
	 */
	private String code;

	/**
	 * 异常信息
	 */
	private String info;

	/**
	 * 构造器
	 * @param code 错误码枚举
	 */
	public BizException(ICodeMsg code) {
		super(code.getMsg());
		this.code = code.getCode();
		this.info = code.getMsg();
	}

	/**
	 * 构造器
	 * @param code 错误码枚举
	 * @param info 错误信息
	 */
	public BizException(ICodeMsg code, String info) {
		super(code.getMsg() + SystemConstant.COLON + info);
		this.code = code.getCode();
		this.info = code.getMsg() + SystemConstant.COLON + info;
	}

	/**
	 * 构造器
	 * @param cause 错误异常类
	 */
	public BizException(final Throwable cause) {
		super(cause);
		this.code = CodeMsg.SYSTEM_ERR.getCode();
		this.info = CodeMsg.SYSTEM_ERR.getMsg();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
