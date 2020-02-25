package red.lixiang.tools.common.mybatis.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识这个字段查询sql的时候要用
 * @author lixiang
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QC {

    String fieldName() default "" ;

    /** 主要是为了数据库索引考虑,如果是 in (123,456) 就要传 Long.class  */
    Class<?> classType() default String.class;

    /** 是否是list的查询 */
    boolean listQuery() default false;

    /** 是否是like匹配查询 */
    boolean likeQuery() default false;

    /** 是否跳过这个字段 */
    boolean skipRich() default false;

    /** 是否是大于 */
    boolean biggerRich() default false;

    /** 是否是小于 */
    boolean smallerRich() default false;

    /** 是否做了加密处理 */
    boolean security() default false;
    /** 加密处理的存salt的表 */
    String salt() default "";
    /** 哪个字段是加密之后的值 */
    String aes() default "";
}
