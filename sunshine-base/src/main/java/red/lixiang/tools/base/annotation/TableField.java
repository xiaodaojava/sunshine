package red.lixiang.tools.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 如果是用bootstrap生成表格的话,则用这个注解
 * @author lixiang
 * @date 2020/7/12
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String title() default "";
}
