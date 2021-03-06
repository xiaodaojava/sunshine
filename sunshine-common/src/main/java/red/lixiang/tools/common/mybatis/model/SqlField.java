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

    /** 字段说明,可用于生成前端表格的数据 */
    String remark() default "";

    /** 表格是否可编辑 */
    boolean tableEditable() default false;

    /** 主键字段,一般是id, 需不需要使用雪花算法生成 */
    boolean snowId() default false;

    boolean createTime() default false;

    boolean updateTime() default false;
}
