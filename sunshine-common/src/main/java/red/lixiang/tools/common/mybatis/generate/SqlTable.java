package red.lixiang.tools.common.mybatis.generate;

import java.util.List;

/**
 * 存的是数据库表对应的实体
 * @Author lixiang
 * @CreateTime 24/03/2018
 **/
public class SqlTable {

    /** 表名 */
    private String tableName;

    /** 对应的java实体类的名称 */
    private String javaTableName;

    /** 对应的java实体类驼峰的名称 */
    private String javaCamelName;

    /** 表里面的字段名 */
    private List<SqlField> fieldList;

    public String getTableName() {
        return tableName;
    }

    public SqlTable setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<SqlField> getFieldList() {
        return fieldList;
    }

    public SqlTable setFieldList(List<SqlField> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public String getJavaTableName() {
        return javaTableName;
    }

    public SqlTable setJavaTableName(String javaTableName) {
        this.javaTableName = javaTableName;
        return this;
    }

    public String getJavaCamelName() {
        return javaCamelName;
    }

    public SqlTable setJavaCamelName(String javaCamelName) {
        this.javaCamelName = javaCamelName;
        return this;
    }
}
