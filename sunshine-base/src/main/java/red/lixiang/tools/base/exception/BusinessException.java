package red.lixiang.tools.base.exception;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixiang
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Throwable throwable;

    private String errorMessage;

    private String errorCode;

    /**
     * 用于保存发生异常时的一些场景信息
     */
    private Object env;

    private List<Object> args;


    /**
     * 一种是纯MSG的
     * @param msg
     */
    public BusinessException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }


    /**
     * 消息+异常。 这种和上面，对应着，没有定义枚举的场景
     * @param msg
     * @param exception
     */
    public BusinessException(String msg, Throwable exception) {
        super(msg,exception);
        this.throwable = exception;
        this.errorMessage = msg;
    }




    public BusinessException(BaseErrorMsg e , Object... args){
        this.errorCode = e.getCode();
        this.errorMessage = e.getMsg();
        if(args!=null && args.length > 0){
            this.args = new ArrayList<>();
            this.args.addAll(Arrays.asList(args));
        }
    }



    public Throwable getThrowable() {
        return throwable;
    }

    public BusinessException setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BusinessException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public BusinessException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object getEnv() {
        return env;
    }

    public BusinessException setEnv(Object env) {
        this.env = env;
        return this;
    }
}
