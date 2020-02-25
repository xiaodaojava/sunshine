package red.lixiang.tools.common.mybatis.generate;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import red.lixiang.tools.common.thymeleaf.TplConfig;
import red.lixiang.tools.jdk.StringTools;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.*;

/**
 * @Author lixiang
 * @CreateTime 24/03/2018
 **/
public class MybatisGenerateUtils {

    private DataSource dataSource;

    /** 存数据库类型和java类型的映射 */
    private Map<String,String> property2BeanMap = new HashMap<>();

    public MybatisGenerateUtils(DataSource dataSource) {
        this.dataSource = dataSource;
        property2BeanMap.put("BIGINT UNSIGNED","Long");
        property2BeanMap.put("DATETIME","Date");
        property2BeanMap.put("TIMESTAMP","Date");
        property2BeanMap.put("VARCHAR","String");
        property2BeanMap.put("DECIMAL","BigDecimal");
        property2BeanMap.put("BIGINT","Long");
        property2BeanMap.put("TEXT","String");
        property2BeanMap.put("TINYINT","Integer");
        property2BeanMap.put("INT","Integer");
        property2BeanMap.put("BIT","Integer");
        property2BeanMap.put("CHAR","String");
        property2BeanMap.put("DOUBLE","Double");
    }


    /**
     * 获取mysql里面的表信息
     * 本来这个方法是不公开的,但是为了模块个性化定制,写是写成了public
     *
     * @param tableName
     * @return
     */
    public  SqlTable getTableInfo(String dbName, String tableName) {

        SqlTable table = new SqlTable();
        //把tableName转成驼峰式,并且首子母改成大写
        table.setJavaCamelName(StringTools.underScope2Camel(tableName));
        String javaTableName = StringTools.first2BigLetter(table.getJavaCamelName());
        table.setTableName(tableName).setJavaTableName(javaTableName);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();

            DatabaseMetaData metaData = conn.getMetaData();
            //获取索引数据,凡是有索引的,都要放到QC对象里面
            ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, false, true);
            List<String> indexList = new ArrayList<>(10);
            while (indexInfo.next()){
                if (!dbName.equals(indexInfo.getString("TABLE_CAT"))) {
                    continue;
                }
                String columnName = indexInfo.getString("COLUMN_NAME");
                indexList.add(columnName);
            }
            // 获取列信息
            ResultSet resultSet = metaData.getColumns(null, null, tableName, "%");
            String columnName;
            String columnType;

            List<SqlField> fieldList = new ArrayList<>();
            while (resultSet.next()) {
                if (!resultSet.getString("TABLE_CAT").equals(dbName)) {
                    continue;
                }
                SqlField field = new SqlField();
                columnName = resultSet.getString("COLUMN_NAME");
                columnType = resultSet.getString("TYPE_NAME");
                String autoincrementFlag = resultSet.getString("IS_AUTOINCREMENT");
                field.setQueryFlag("YES".equals(autoincrementFlag) ||indexList.contains(columnName) );
                String remark = resultSet.getString("REMARKS");
                field.setName(columnName).setRemark(remark).setType(columnType);
                field.setCamelName(StringTools.underScope2Camel(columnName));
                field.setJavaName(StringTools.first2BigLetter(field.getCamelName()));
                field.setJavaType(property2BeanMap.get(columnType));
                fieldList.add(field);
            }
            table.setFieldList(fieldList).setTableName(tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return table;
    }

    /**
     * 默认的自动生成方法,
     * @param dbName
     * @param tableName
     * @param packageName 包的名字
     */
    public void defaultGenerate(String dbName, String tableName,String packageName){
        defaultGenerate(dbName, tableName, packageName,null);
    }

    public void defaultGenerate(String dbName, String tableName,String packageName,String domainName){
        defaultGenerate(dbName, tableName, packageName, domainName,false);
    }

    public void defaultGenerate(String dbName, String tableName,String packageName,String domainName,boolean autoFilePath){
        //获取文件分隔符
        String f = System.getProperty("file.separator");

        SqlTable tableInfo = getTableInfo(dbName, tableName);
        //初始化thymeleaf
        TemplateEngine engine = TplConfig.getTemplateEngine();
        final Context ctx = new Context(Locale.CHINA);
        Map<String, Object> map = new HashMap<>(1);
        map.put("table",tableInfo);
        ctx.setVariables(map);
        ctx.setVariable("basePackage",packageName);
        // 默认的领域的名称就是表的名字全小写
        ctx.setVariable("domainName",tableInfo.getJavaTableName().toLowerCase());
        if(domainName!=null){
            ctx.setVariable("domainName",domainName);
        }
        String model = engine.process("text/mybatis/mysql-model.txt", ctx);
        String qc = engine.process("text/mybatis/mysql-qc.txt", ctx);
        String dao = engine.process("text/mybatis/mysql-dao-java.txt", ctx);
        String provider = engine.process("text/mybatis/mysql-provider.txt", ctx);
        String manager = engine.process("text/mybatis/mysql-manager-java.txt", ctx);
        String managerImpl = engine.process("text/mybatis/mysql-manager-impl-java.txt", ctx);
        String controller = engine.process("text/mybatis/mysql-controller-java.txt", ctx);
        //在本地目录生成文件
        try {
            final Map<String, String> pathMap  = new HashMap<>(6);
            String currentDir  = System.getProperty("user.dir");
            currentDir=currentDir.substring(0,currentDir.lastIndexOf(f))+f;
            pathMap.put("model",currentDir);
            pathMap.put("qc",currentDir);
            pathMap.put("mapper",currentDir);
            pathMap.put("provider",currentDir);
            pathMap.put("manager",currentDir);
            pathMap.put("managerImpl",currentDir);
            pathMap.put("controller",currentDir);
            //如果设置了自动路径,就YY一下路径
            if(autoFilePath){
                Files.list(Paths.get(currentDir)).forEach(file->{
                    if(Files.isDirectory(file)){
                        String fileName = file.toString();

                        if(fileName.endsWith("admin")){
                            pathMap.put("controller",fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/admin/controller/");
                        }
                        if(fileName.endsWith("business")){
                            pathMap.put("model",fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/models/dos/");
                            pathMap.put("qc",fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/models/qc/");
                            pathMap.put("manager",fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/business/manager/");
                        }
                        if(fileName.endsWith("business-impl")){
                            //这种带有domainName的要选创建文件夹
                            String managerImplPath = fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/business/manager/impl/"+domainName+"/";
                            String mapperPath =  fileName+"/src/main/java/"+packageName.replaceAll("\\.","/")+"/dao/"+domainName+"/";
                            pathMap.put("managerImpl",managerImplPath);
                            pathMap.put("mapper",mapperPath);
                            pathMap.put("provider",mapperPath);
                        }
                    }

                });
            }
            try {
                Files.createDirectories(Paths.get(pathMap.get("model")));
                Files.createDirectories(Paths.get(pathMap.get("qc")));
                Files.createDirectories(Paths.get(pathMap.get("mapper")));
                Files.createDirectories(Paths.get(pathMap.get("provider")));
                Files.createDirectories(Paths.get(pathMap.get("manager")));
                Files.createDirectories(Paths.get(pathMap.get("managerImpl")));
                Files.createDirectories(Paths.get(pathMap.get("controller")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Files.write(Paths.get(pathMap.get("model")+tableInfo.getJavaTableName() + "DO.java"),model.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("qc")+tableInfo.getJavaTableName() + "QC.java"),qc.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("mapper")+tableInfo.getJavaTableName() + "Mapper.java"),dao.getBytes(),StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("provider")+tableInfo.getJavaTableName() + "Provider.java"),provider.getBytes(),StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("manager")+tableInfo.getJavaTableName() + "Manager.java"),manager.getBytes(),StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("managerImpl")+tableInfo.getJavaTableName() + "ManagerImpl.java"),managerImpl.getBytes(),StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(Paths.get(pathMap.get("controller")+tableInfo.getJavaTableName() + "Controller.java"),controller.getBytes(),StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过传入的sql，获取返回值列表，生成bean
     *
     * @param sql
     */
    public  List<SqlField> getFieldList(String sql) {
        List<SqlField> list = new ArrayList<>();
        try {

            Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                SqlField field = new SqlField();
                String columnName = metaData.getColumnName(i+1);
                String columnType = metaData.getColumnTypeName(i + 1);
                field.setName(columnName).setType(columnType);
                field.setCamelName(StringTools.underScope2Camel(columnName));

                list.add(field);
            }
            return list;

        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return list;

    }



    public MybatisGenerateUtils setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }
}
