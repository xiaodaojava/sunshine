package red.lixiang.tools.spring.controller;

import red.lixiang.tools.base.KV;

import java.util.List;

/**
 * @author lixiang
 * @date 2020/8/4
 **/
public class FrontInsertField {


    /** 类型,1:input框,2:下拉框,3:TextArea */
    private Integer fieldType;

    /** 如果是下拉框的话,对应要去后台查的属性 */
    private String  property;

    /** 插入的标签,如果原注解里面没有配置的话,就使用 title/remark */
    private String label;

    /** 对应java实体类的名称 */
    private String fieldName;

    /** 如果是下拉框的话,下拉的选项 */
    private List<KV> selectList;

    public List<KV> getSelectList() {
        return selectList;
    }

    public FrontInsertField setSelectList(List<KV> selectList) {
        this.selectList = selectList;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FrontInsertField setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public FrontInsertField setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public FrontInsertField setProperty(String property) {
        this.property = property;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public FrontInsertField setLabel(String label) {
        this.label = label;
        return this;
    }
}
