package cc.abing.abstart.support.system.exception;

import cc.abing.abstart.support.system.response.CodeMsg;

public class ABParamException extends ABException {

    private static final long serialVersionUID = -6367799984540410953L;

    /**
     * 构造器
     *
     * @param info 错误信息
     */
    public ABParamException(String info) {
        super(CodeMsg.BAD_REQUEST, info);
    }

}
