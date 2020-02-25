package red.lixiang.tools.common.mybatis.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于放在DO上面
 * 标识这些字段是和数据库相对应的,在插入和更新的时候
 * 只根据这个字段来映射
 * @Author lixiang
 * @CreateTime 2019/9/3
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlField {

    /** 是否做了加密处理 */
    boolean security() default false;
    /** 加密处理的存salt的表 */
    String salt() default "";
    /** 哪个字段是加密之后的值 */
    String aes() default "";
}
