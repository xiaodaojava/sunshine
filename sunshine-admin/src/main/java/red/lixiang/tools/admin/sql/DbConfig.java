package red.lixiang.tools.admin.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DbConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "h2DataSource")
    public DataSource dataSource(){
        //org.h2.jdbcx.JdbcDataSource
        // http://localhost:9999
        HikariConfig config = new HikariConfig();
//        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        return new HikariDataSource(config);
    }

    @Bean(initMethod = "start" , destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        try {
            Connection connection = dataSource.getConnection();
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:ddlScript/passport.sql");
            EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
            ScriptUtils.executeSqlScript(connection,encodedResource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Server.createWebServer("-webPort","9999");
    }



}
