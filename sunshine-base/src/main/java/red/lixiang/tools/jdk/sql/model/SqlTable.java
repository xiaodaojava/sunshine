package red.lixiang.tools.jdk.sql.model;

import java.util.List;

/**
 * 存的是数据库表对应的实体
 * @Author lixiang
 * @CreateTime 24/03/2018
 **/
public class SqlTable {

    /** 数据库名称 */
    public String schemeName;

    /** 表名 test_table */
    public String tableName;

    /** 对应的java实体类的名称 TestTable */
    public String javaTableName;

    /** 对应的java实体类驼峰的名称 testTable */
    public String javaCamelName;

    /** 表的备注 */
    public String tableRemark;

    /** 表里面的字段名 */
    public List<SqlField> fieldList;

    public String getTableRemark() {
        return tableRemark;
    }

    public SqlTable setTableRemark(String tableRemark) {
        this.tableRemark = tableRemark;
        return this;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public SqlTable setSchemeName(String schemeName) {
        this.schemeName = schemeName;
        return this;
    }

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
