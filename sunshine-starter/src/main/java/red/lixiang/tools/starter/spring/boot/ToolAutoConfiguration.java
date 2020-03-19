package red.lixiang.tools.starter.spring.boot;

import red.lixiang.tools.jdk.ThreadPoolTools;
import red.lixiang.tools.spring.redis.RedisSpringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lixiang
 */
@Configuration
@EnableConfigurationProperties(ToolsProperty.class)
public class ToolAutoConfiguration {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ToolsProperty toolsProperty;


    @Bean
    public RedisSpringTools getRedisUtil(){
        stringRedisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return new RedisSpringTools().setRedisTemplate(stringRedisTemplate);
    }


    @Bean
    public ThreadPoolTools getThreadPoolTools(){
        return new ThreadPoolTools();
    }


//    @Bean
//    @ConditionalOnClass(SqlSessionFactory.class)
//    @ConditionalOnProperty(prefix = "tools",name = "salt-table")
//    public SecurityInterceptor getSecurityInterceptor(){
//        try {
//            JdbcTemplate jdbcTemplate = ContextHolder.getBean(JdbcTemplate.class).get();
//            String tableName = toolsProperty.getSaltTable();
//            List<KV> kvList = jdbcTemplate.query("select * from "+tableName, new BeanPropertyRowMapper<>(KV.class));
//            if(!CollectionUtils.isEmpty(kvList)){
//                AESTools.initSalt(kvList);
//            }
//            MapperUtils.securityFlag=true;
//        }catch (Exception e){
//            System.out.println("初始化salt失败,请检查表名是否为:"+toolsProperty.getSaltTable());
//        }
//
//        return new SecurityInterceptor();
//    }



}
