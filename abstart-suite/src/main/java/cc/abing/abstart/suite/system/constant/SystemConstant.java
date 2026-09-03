package cc.abing.abstart.suite.system.constant;

public final class SystemConstant {

	/**
	 * 基础路径
	 */
	public static final String BASE_PATH = "/api/v1";

	/**
	 * 默认分页大小
	 */
	public static final Integer PAGE_SIZE = 50;

	/**
	 * 空白
	 */
	public static final String BLANK_SPACE = " ";

	/**
	 * 中文冒号
	 */
	public static final String COLON = "：";

	/**
	 * 逗号
	 */
	public static final String COMMA = ",";

	/**
	 * 连字号
	 */
	public static final String HYPHEN = "-";

	/**
	 * 等于号
	 */
	public static final String EQUAL = "=";

	/**
	 * 左尖括号
	 */
	public static final String LEFT_ANGLE_BRACKETS = "<";

	/**
	 * 右尖括号
	 */
	public static final String RIGHT_ANGLE_BRACKETS = ">";

	/**
	 * 未知
	 */
	public static final String UNKNOWN = "unKnown";

	/**
	 * 成功标志
	 */
	public static final String SUCCESS = "Y";

	/**
	 * 失败标志
	 */
	public static final String FAIL = "N";

	/**
	 * 客户端时间戳请求头 13位
	 */
	public static final String HEADER_TIMESTAMP = "X-Timestamp";

	/**
	 * 客户端随机串请求头（防重放）
	 */
	public static final String HEADER_NONCE = "X-Nonce";

	/**
	 * 客户端版本请求头
	 */
	public static final String HEADER_VERSION = "X-Version";

	/**
	 * 客户端设备标识请求头
	 */
	public static final String HEADER_DEVICE_ID = "X-Device-Id";

	private SystemConstant() {
	}

}
