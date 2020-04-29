package red.lixiang.tools.jdk.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author lixiang
 * @date 2020/3/27
 **/
public class SqlConfig {
    private Long id;
    private String tag;
    private String url;
    private String username;
    private String password;
    private Integer port = 3306;
    /** 应该适用于对info库没有权限,只能连上某一特定的库 */
    private String dbName;

    /** 要对哪个数据库进行操作 */
    private String targetDb;

    private Connection conn;

    public void close(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public Connection conn(){
        //获取数据库连接
        try {
            if (conn!=null && conn.isValid(1)) {
                return conn;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            String connUrl;
            if(dbName!=null){
                connUrl = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=utf-8&serverTimezone=GMT%%2B8",url,port,dbName);
            }else {
                connUrl = String.format("jdbc:mysql://%s:%d?characterEncoding=utf-8&serverTimezone=GMT%%2B8",url,port);
            }
            conn = DriverManager.getConnection(connUrl,username,password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTargetDb() {
        return targetDb;
    }

    public SqlConfig setTargetDb(String targetDb) {
        this.targetDb = targetDb;
        return this;
    }

    public Long getId() {
        return id;
    }

    public SqlConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public SqlConfig setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SqlConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SqlConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SqlConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public SqlConfig setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public SqlConfig setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }
}
