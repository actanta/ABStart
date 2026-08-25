package cc.abing.abstart.suite.system.response;

public interface ICodeMsg {

	/**
	 * 返回错误码
	 * @return 错误码
	 */
	String getCode();

	/**
	 * 返回错误信息
	 * @return 错误信息
	 */
	String getMsg();

}