package red.lixiang.tools.base.exception;

/**
 * 这个类是一个静态常量，做一些异常的error_code和msg 的声明
 *
 * 整体异常定义说明
 *
 *登录相关的的异常以 LOGIN_开, code 为 1XX
 *
 *dubbo相关的异常以 RPC_开头, code 为 3XX
 *
 *数据库相关的异常以 SQL_开头, code 为 4XX
 *
 *业务逻辑相关的异常以 SERVICE_开头, code 为 5XX
 *
 *参数校验相关的异常以 PARA_开头， code 为 7XX
 *
 *
 *
 * @Author lixiang
 * @CreateTime 2019-03-05
 **/
public enum ExceptionEnum implements BaseErrorMsg {

    /**
     * 登录密码错误
     */
    LOGIN_WRONG_PASSWORD("101","登录密码错误"),


    SQL_TRANSACTION_FAIL("401","数据库事务未开启"),


    PARA_CHECK_FAIL("701","参数校验错误[%s]");




    private String errorMsg;

    private String errorCode;

    ExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getCode() {
        return errorCode;
    }

    @Override
    public String getMsg() {
        return errorMsg;
    }
}
