package red.lixiang.tools.jdk.sql.model;


/**
 * 数据库对应
 * @Author lixiang
 * @CreateTime 24/03/2018
 **/
public class SqlField {

    /**字段名称 passport_role */
    private String name;

    /** 字段的排序 */
    private Integer ordinary;

    /**驼蜂式命名 passportRole */
    private String camelName;

    /**字段类型 varchar */
    private String type;

    /**java实体类的类型 String*/
    private String javaType;

    /** 首字母大写的字段名 PassportRole */
    private String javaName;

    /**备注*/
    private String remark;

    /** 数据库类型 */
    private String jdbcType;

    /** 标明是不是查询对象 */
    private Boolean queryFlag;

    public Integer getOrdinary() {
        return ordinary;
    }

    public SqlField setOrdinary(Integer ordinary) {
        this.ordinary = ordinary;
        return this;
    }

    public String getJavaName() {
        return javaName;
    }

    public SqlField setJavaName(String javaName) {
        this.javaName = javaName;
        return this;
    }

    public String getName() {
        return name;
    }

    public SqlField setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public SqlField setType(String type) {
        this.type = type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SqlField setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCamelName() {
        return camelName;
    }

    public SqlField setCamelName(String camelName) {
        this.camelName = camelName;
        return this;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public SqlField setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
        return this;
    }

    public String getJavaType() {
        return javaType;
    }

    public SqlField setJavaType(String javaType) {
        this.javaType = javaType;
        return this;
    }

    public Boolean getQueryFlag() {
        return queryFlag;
    }

    public SqlField setQueryFlag(Boolean queryFlag) {
        this.queryFlag = queryFlag;
        return this;
    }
}
