package red.lixiang.tools.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lixiang
 * @date 2019/12/11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
public @interface EnhanceTool {
    boolean logParam() default true;

    boolean logResult() default true;

    boolean tryCatch() default true;

    boolean skipToMap() default false;

    /**
     * 可以指定在toMap的时候,使用指定的key
     *
     * @return
     */
    String mapKey() default "";

    /***
     *   这种是用转化器进行转化
     * EnhanceTool(convertor= ,source=, targetIdentify=,)
     */
    Class<?> convertor() default Object.class;

    /**
     * 这种是直接用实体类进行转化
     * Hospital类的 name 字段,条件是id
     * EnhanceTool(targetEntity= ,source=, targetField= ,targetIdentify=,)
     */
    Class<?> targetEntity() default Object.class;

    /**
     * 这个要是下划线的形式
     */
    String targetField() default "";

    /**
     * 这个要是下划线的形式
     */
    String targetIdentity() default "id";


    // 源头的值是哪一个
    String source() default "";
}
