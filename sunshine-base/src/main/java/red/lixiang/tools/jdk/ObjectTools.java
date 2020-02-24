package red.lixiang.tools.jdk;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author lixiang
 * @CreateTime 2019/10/2
 **/
public class ObjectTools {



    /**
     * 如果传入的值不为空,则返回传入值,否则返回defaultValue
     * @param t
     * @param defaultValue
     * @param <T>
     * @return
     */
    public  static<T> T getOrDefault(T t,T defaultValue){
        return t== null ? defaultValue:t;
    }

    /**
     * 注意,这个方法会吃掉空指针异常
     * 对传进去的p进行mapFunction操作,如果最后结果为null 或者中间有空指针异常的话,会返回默认值
     * 如结果不为null , 则返回结果
     * @param p
     * @param defaultValue
     * @param mapFunction
     * @param <R>
     * @param <P>
     * @return
     */
    public  static<R,P> R getOrDefault(P p ,R defaultValue,Function<P,R> mapFunction){
        try {
            R t= mapFunction.apply(p);
            defaultValue = t ==null?defaultValue:t;
        }catch (NullPointerException e){
            e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    public static void main(String[] args) {
        String de = getOrDefault("original","default",(x)->{
            String result = null;
            result.substring(0,0);
            return result;
        });
        System.out.println(de);
    }
}
