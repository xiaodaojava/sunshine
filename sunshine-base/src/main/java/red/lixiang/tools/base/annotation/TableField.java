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

    public static final int TYPE_INPUT = 1;
    public static final int TYPE_SELECT = 2;
    public static final int TYPE_TEXTAREA = 3;

    /** 是否展示为表格的表头 */
    String title() default "";

    /** 是否可编辑 */
    boolean editable() default false;

    /** 单元格格式 */
    String cellStyle() default "";

    /** 是否自动生成新增的html */
    boolean addField() default true;

    /** 如果是下拉框的话,对应的属性 */
    String property() default "";

    /** 插入框前面的label */
    String label() default "";

    int fieldType() default 1;

}
