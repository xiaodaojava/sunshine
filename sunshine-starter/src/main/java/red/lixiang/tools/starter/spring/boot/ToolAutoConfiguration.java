package red.lixiang.tools.starter.spring.boot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.jdk.ThreadPoolTools;
import red.lixiang.tools.spring.AOPTools;
import red.lixiang.tools.spring.ContextHolder;
import red.lixiang.tools.spring.mybatis.MybatisTools;
import red.lixiang.tools.spring.oss.AliOssTools;
import red.lixiang.tools.spring.redis.RedisSpringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;
import java.util.Optional;

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
//    @ConditionalOnClass(name = "com.aliyun.oss.OSSClient")
//    public AliOssTools getOssTools(){
//        AliOssTools ossTools = new AliOssTools();
//        ossTools.setToolsProperty(toolsProperty);
//        return ossTools.init();
//    }

    @Bean
    @ConditionalOnClass(SqlSessionFactory.class)
    public MybatisTools getSecurityInterceptor(){
        SqlSessionFactory sessionFactory = ContextHolder.getBean(SqlSessionFactory.class).get();
        org.apache.ibatis.session.Configuration configuration = sessionFactory.getConfiguration();
        MybatisTools tools = new MybatisTools(configuration);
        ApplicationContext applicationContext = ContextHolder.getApplicationContext();
        Map<String, Object> mapperList = applicationContext.getBeansWithAnnotation(Mapper.class);
        mapperList.forEach((key,value)->{
            BaseMapper baseMapper = (BaseMapper) value;
            // 初始化一些缓存信息
            baseMapper.getMapperClass();
            // 从BaseMapper中获取真正的类,这时候的target是
            Class<?> target = AOPTools.getTarget(baseMapper);
            tools.injectMapper(target);
        });
        return tools;
    }



}
