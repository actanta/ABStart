package cc.abing.abstart.support.system.error;

public class ABParamException extends ABException{

    /**
     * 构造器
     * @param info 错误信息
     */
    public ABParamException(String info) {
        super(CodeInfo.BAD_REQUEST);
        super.setInfo(info);
    }

}
