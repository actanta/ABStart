package cc.abing.abstart.support.system.error;

import cc.abing.abstart.support.system.constant.SystemConstant;

public class ABException extends RuntimeException {

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
     *
     * @param code 错误码枚举
     */
    public ABException(Code code) {
        super(code.getInfo());
        this.code = code.getCode();
        this.info = code.getInfo();
    }

    /**
     * 构造器
     *
     * @param code 错误码枚举
     * @param info 错误信息
     */
    public ABException(Code code, String info) {
        super(code.getInfo() + SystemConstant.COLON + info);
        this.code = code.getCode();
        this.info = code.getInfo() + SystemConstant.COLON + info;
    }

    /**
     * 构造器
     *
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
