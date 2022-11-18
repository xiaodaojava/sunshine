package red.lixiang.tools.spring.transaction;

import org.springframework.transaction.support.TransactionSynchronizationManager;
import red.lixiang.tools.base.exception.ExceptionEnum;
import red.lixiang.tools.base.exception.XdPreconditions;

public class TransactionTools {

    /**
     *判断当前有没有事务开启
     */
    public static void checkTransaction(){
        XdPreconditions.checkArgument(TransactionSynchronizationManager.isActualTransactionActive(), ExceptionEnum.SQL_TRANSACTION_FAIL);
    }
}
