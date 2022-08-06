package cc.abing.abstart.support.system.error;

public class ABParamException extends ABException {

    private static final long serialVersionUID = -6367799984540410953L;

    /**
     * 构造器
     *
     * @param info 错误信息
     */
    public ABParamException(String info) {
        super(CodeInfo.BAD_REQUEST, info);
    }

}
