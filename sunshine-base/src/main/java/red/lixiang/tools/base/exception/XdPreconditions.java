package red.lixiang.tools.base.exception;


import red.lixiang.tools.jdk.StringTools;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 常用的一些断言
 */
public class XdPreconditions {

    private XdPreconditions() {
    }

    /**
     * 快速校验
     * @param b
     * @param errorMsg
     * @param obj
     * @param <T>
     */
    public static <T> void checkArgument(boolean b , BaseErrorMsg errorMsg,Object... obj){
        if(!b){
            throw new BusinessException(errorMsg,obj);
        }
    }

    /**
     * 期望的值
     * @param t
     * @param predicate
     * @param errorMsg
     * @param obj
     * @param <T>
     */
    public static <T> void checkArgument(T t , Predicate<T> predicate , BaseErrorMsg errorMsg,Object... obj){
        if(!predicate.test(t)){
            throw new BusinessException(errorMsg,obj);
        }
    }




    public static void checkNotNull(Object obj,Object param){
        if(obj == null){
            throw new BusinessException(ExceptionEnum.PARA_CHECK_FAIL,param);
        }

        if(obj instanceof  String){
            String str = (String) obj;
            if(StringTools.isBlank(str)){
                throw new BusinessException(ExceptionEnum.PARA_CHECK_FAIL,param);
            }
        }

        if(obj instanceof List){
            List listObj = (List)obj;
            if(listObj.size() == 0){
                throw new BusinessException(ExceptionEnum.PARA_CHECK_FAIL,param);
            }
        }

        if(obj instanceof Map){
            Map mapObj = (Map)obj;
            if(mapObj.size() == 0){
                throw new BusinessException(ExceptionEnum.PARA_CHECK_FAIL,param);
            }
        }
    }




}
