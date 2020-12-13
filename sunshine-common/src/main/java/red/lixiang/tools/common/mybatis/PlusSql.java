package red.lixiang.tools.common.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lixiang
 * @date 2020/6/27
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface PlusSql {
    /** sql 的类别 ,有 select , insert, update , delete */
    String sqlType() default "select";

    /** 拼在后面的where条件, */
    String[] whereParam() default {};


}
