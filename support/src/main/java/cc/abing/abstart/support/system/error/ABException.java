package cc.abing.abstart.support.system.error;

public class ABException extends RuntimeException{

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
    public ABException(Code code) {
        this.code = code.getCode();
        this.info = code.getInfo();
    }

    /**
     * 构造器
     * @param cause 错误异常类
     */
    public ABException(final Throwable cause) {
        super(cause);
        this.code = CodeInfo.SYSTEM_ERR.getCode();
        this.info = CodeInfo.SYSTEM_ERR.getInfo();
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
