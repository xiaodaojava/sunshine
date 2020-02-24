package red.lixiang.tools.jdk.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 无论是实体类转xml还是xml转实体类的时候
 * 在复杂对象中, 都不看xmlRoot属性,只看xmlEle属性
 * xmlRoot只在当前类是最顶层的时候才用
 *
 * @author lixiang
 * @date 2020/1/16
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.PARAMETER,ElementType.FIELD})
public @interface XmlField {

    /**
     * 这种适用于复杂嵌套对象
     * @return
     */
    String xmlRoot() default "";

    /**
     * 这种适用于简单对象,如String,Int 等
     * @return
     */
    String xmlEle() default "";

    /**
     * 适用于List中单个详情的,也适用于单个的再加一层
     * xmlEle = lists, row = row
     * List(Model) list;
     * lists->
     *  row->
     *     axxxxx
     *     bxxxx
     *  row/
     * lists/
     * 单个的再加一层
     * xmlEle = model, row = row
     * Model model;
     *
     * model ->
     *  row->
     *      xxxx
     *  row/
     * model/
     *
     * @return
     */
    String row() default "";

    /**
     * 说明
     * @return
     */
    String desc() default "";

}
