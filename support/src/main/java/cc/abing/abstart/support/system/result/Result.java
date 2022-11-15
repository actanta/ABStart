package cc.abing.abstart.support.system.result;

import cc.abing.abstart.support.system.response.CodeMsg;
import cc.abing.abstart.support.system.response.ICodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ABing
 * @since 2022/10/17
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

	public static <T> Result<T> success(T data) {
		return new Result<>(CodeMsg.SYSTEM_OK.getCode(), CodeMsg.SYSTEM_OK.getMsg(), data);
	}

	public static <T> Result<T> success(String message, T data) {
		return new Result<>(CodeMsg.SYSTEM_OK.getCode(), message, data);
	}

	public static Result<?> failed() {
		return new Result<>(CodeMsg.BAD_REQUEST.getCode(), CodeMsg.BAD_REQUEST.getMsg(), null);
	}

	public static Result<?> failed(String message) {
		return new Result<>(CodeMsg.BAD_REQUEST.getCode(), message, null);
	}

	public static Result<?> failed(ICodeMsg codeMsg) {
		return new Result<>(codeMsg.getCode(), codeMsg.getMsg(), null);
	}

	public static <T> Result<T> instance(String code, String message, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMsg(message);
		result.setData(data);
		return result;
	}

}
