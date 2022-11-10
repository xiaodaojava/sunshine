package red.lixiang.tools.jdk.sql;

import red.lixiang.tools.jdk.ListTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.ToolsLogger;
import red.lixiang.tools.jdk.sql.model.SqlField;
import red.lixiang.tools.jdk.sql.model.SqlScheme;
import red.lixiang.tools.jdk.sql.model.SqlTable;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author lixiang
 * @date 2020/4/7
 **/
public class SqlTools {

    public static void main(String[] args) throws IOException {

    }

    /** 存数据库类型和java类型的映射 */
    private static Map<String,String> property2BeanMap = new HashMap<>();

    static  {
        property2BeanMap.put("BIGINT UNSIGNED","Long");
        property2BeanMap.put("DATETIME","Date");
        property2BeanMap.put("TIMESTAMP","Date");
        property2BeanMap.put("VARCHAR","String");
        property2BeanMap.put("DECIMAL","BigDecimal");
        property2BeanMap.put("BIGINT","Long");
        property2BeanMap.put("TEXT","String");
        property2BeanMap.put("TINYINT","Integer");
        property2BeanMap.put("SMALLINT","Integer");
        property2BeanMap.put("INT","Integer");
        property2BeanMap.put("BIT","Integer");
        property2BeanMap.put("CHAR","String");
        property2BeanMap.put("DOUBLE","Double");
    }


    public static void compareScheme(List<SqlTable> t1, List<SqlTable> t2) {
        for (SqlTable ceshiTable : t1) {

            List<SqlTable> tableList = ListTools.containsByField(t2, "tableName", ceshiTable.getTableName(), SqlTable.class);
            if (ListTools.isBlank(tableList)) {
                ToolsLogger.plainInfo("t2没有表{}", ceshiTable.getTableName());
                continue;
            }
            SqlTable onlineTable = tableList.get(0);

            for (SqlField sqlField : ceshiTable.getFieldList()) {
                List<SqlField> name = ListTools.containsByField(onlineTable.getFieldList(), "name", sqlField.getName(), SqlField.class);
                if (ListTools.isBlank(name)) {
                    ToolsLogger.plainInfo("t2没有的表{},字段{}", ceshiTable.getTableName(), sqlField.getName());
                }
            }
        }
    }

    public static List<SqlScheme> schemeInfo(SqlConfig sqlConfig){

        return schemeInfo(sqlConfig.conn());
    }
    public static List<SqlScheme> schemeInfo(Connection conn){
        List<SqlScheme> schemeList = new ArrayList<>();
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();
            while (resultSet.next()){
                SqlScheme sqlScheme = new SqlScheme();
                sqlScheme.setName(resultSet.getString("TABLE_CAT"));
                schemeList.add(sqlScheme);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return schemeList;
    }


    public static List<SqlTable> tableInfo(String schemeName, SqlConfig sqlConfig) {
        return tableInfo(schemeName,sqlConfig.conn());
    }
    public static List<SqlTable> tableInfo(String schemeName, Connection conn) {
        List<SqlTable> tableList = new ArrayList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(schemeName, schemeName, null, null);
            while (resultSet.next()) {
                SqlTable table = new SqlTable();
                // test_table
                String tableName = resultSet.getString("TABLE_NAME");
                table.setTableName(tableName);
                table.setTableRemark(resultSet.getString("REMARKS"));
                table.setSchemeName(schemeName);
                // testTable
                table.setJavaCamelName(StringTools.underScope2Camel(tableName));
                // TestTable
                table.setJavaTableName(StringTools.first2BigLetter(table.getJavaCamelName())) ;
                tableList.add(table);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tableList;
    }

    public static List<SqlField> columnInfo(String schemeName, String tableName, SqlConfig sqlConfig) {
        return columnInfo(schemeName, tableName, sqlConfig.conn());
    }
    public static List<SqlField> columnInfo(String schemeName, String tableName, Connection conn) {
        List<SqlField> fieldList = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            //获取索引数据,凡是有索引的,都要放到QC对象里面
            ResultSet indexInfo = metaData.getIndexInfo(schemeName, schemeName, tableName, false, true);
            List<String> indexList = new ArrayList<>(10);
            while (indexInfo.next()) {
                String columnName = indexInfo.getString("COLUMN_NAME");
                indexList.add(columnName);
            }
            // 获取列信息
            resultSet = metaData.getColumns(schemeName, schemeName, tableName, "%");
            String columnName;
            String columnType;

            while (resultSet.next()) {
                SqlField field = new SqlField();
                // test_table
                columnName = resultSet.getString("COLUMN_NAME");
                columnType = resultSet.getString("TYPE_NAME");
                String autoincrementFlag = resultSet.getString("IS_AUTOINCREMENT");
                field.setQueryFlag("YES".equals(autoincrementFlag) || indexList.contains(columnName));
                String remark = resultSet.getString("REMARKS");
                field.setName(columnName).setRemark(remark).setType(columnType);
                // testTable
                field.setCamelName(StringTools.underScope2Camel(columnName));
                // TestTable
                field.setJavaName(StringTools.first2BigLetter(field.getCamelName()));
                field.setJavaType(property2BeanMap.get(columnType));
                fieldList.add(field);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return fieldList;
    }

    /**
     * 去数据库查询表格数据
     *
     * @param sql
     * @param config
     * @return
     */
    public static List<Map<String, Object>> tableDetail(String sql , SqlConfig config){
        return tableDetail(sql,config.getTargetDb(),config.conn());
    }

    public static List<Map<String, Object>> tableDetail(String sql , String targetDb, Connection conn){
        List<Map<String, Object>> result = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            statement.execute("use "+targetDb);
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> colNameList = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                colNameList.add(metaData.getColumnLabel(i+1));
            }
            while (resultSet.next()){
                Map<String, Object> map = new LinkedHashMap<>();
                for (String colName : colNameList) {
                    Object object = resultSet.getObject(colName);
                    map.put(colName,object);
                }
                result.add(map);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return result;
    }

    public static int update(String sql , String targetDb, Connection conn){
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.execute("use "+targetDb);
            return statement.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return 0;
    }
}
