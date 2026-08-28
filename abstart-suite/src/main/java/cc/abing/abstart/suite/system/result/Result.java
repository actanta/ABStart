package cc.abing.abstart.suite.system.result;

import cc.abing.abstart.suite.system.response.CodeMsg;
import cc.abing.abstart.suite.system.response.ICodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ABing
 * @since 2026-08-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements ICodeMsg {

	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private T data;

	public static <T> Result<T> success() {
		return new Result<>(CodeMsg.SYSTEM_OK, null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(CodeMsg.SYSTEM_OK, data);
	}

	public static <T> Result<T> success(String message, T data) {
		return new Result<>(CodeMsg.SYSTEM_OK.getCode(), message, data);
	}

	public static <T> Result<T> failed() {
		return new Result<>(CodeMsg.BAD_REQUEST, null);
	}

	public static <T> Result<T> failed(String message) {
		return new Result<>(CodeMsg.BAD_REQUEST.getCode(), message, null);
	}

	public static <T> Result<T> failed(ICodeMsg codeMsg) {
		return new Result<>(codeMsg.getCode(), codeMsg.getMsg(), null);
	}

	public static <T> Result<T> failed(ICodeMsg codeMsg, T data) {
		return new Result<>(codeMsg.getCode(), codeMsg.getMsg(), data);
	}

	public Result(CodeMsg codeMsg, T data) {
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
		this.data = data;
	}

}
