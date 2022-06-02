package cc.abing.abstart.support.system.error;

public enum CodeInfo implements Code {

    /**
     * 一切正常
     */
    SYSTEM_OK("200","请求成功"),

    /**
     * 错误请求
     */
    BAD_REQUEST("400", "错误请求"),

    /**
     * 未认证
     */
    UNAUTHORIZED("401","未认证"),

    /**
     * 无权访问
     */
    FORBIDDEN("403","无权访问"),

    /**
     * 系统异常
     */
    SYSTEM_ERR("500","系统异常"),

    ;

    private final String code;

    private final String info;

    CodeInfo(String code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }
}