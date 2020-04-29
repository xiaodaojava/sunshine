package red.lixiang.tools.jdk.sql.model;

import java.util.List;

/**
 * 数据库的名字
 * @author lixiang
 * @date 2020/3/27
 **/
public class SqlScheme {
    /** 数据库名 */
    private String name;
    /** 数据库字符集 */
    private String charset;
    /** 数据库查询比较规则 */
    private String collect;
    /** 数据库中的表 */
    private List<SqlTable> tableList;

    public String getName() {
        return name;
    }

    public SqlScheme setName(String name) {
        this.name = name;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public SqlScheme setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getCollect() {
        return collect;
    }

    public SqlScheme setCollect(String collect) {
        this.collect = collect;
        return this;
    }

    public List<SqlTable> getTableList() {
        return tableList;
    }

    public SqlScheme setTableList(List<SqlTable> tableList) {
        this.tableList = tableList;
        return this;
    }
}
