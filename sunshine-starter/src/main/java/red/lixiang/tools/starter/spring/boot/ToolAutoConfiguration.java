package red.lixiang.tools.starter.spring.boot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import red.lixiang.tools.common.alioss.AliOssTools;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.jdk.thread.ThreadPoolTools;
import red.lixiang.tools.spring.AOPTools;
import red.lixiang.tools.spring.ContextHolder;
import red.lixiang.tools.spring.mybatis.MybatisTools;
import red.lixiang.tools.spring.redis.RedisSpringTools;

import java.util.Map;

/**
 *
 * BaseSimpleController交给子项目去导入
 *
 * @author lixiang
 */
@Configuration
@EnableConfigurationProperties(ToolsProperty.class)
public class ToolAutoConfiguration {


    @Autowired
    private ToolsProperty toolsProperty;


    @Bean
    @ConditionalOnProperty(prefix = "spring.redis",name = "host")
    public RedisSpringTools initRedisSpringTools(){
        StringRedisTemplate stringRedisTemplate = ContextHolder.getBean(StringRedisTemplate.class).get();
        stringRedisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return new RedisSpringTools().setRedisTemplate(stringRedisTemplate);
    }


    @Bean
    public ThreadPoolTools getThreadPoolTools(){
        return new ThreadPoolTools();
    }

    @Bean
    @ConditionalOnProperty(prefix = "tools.oss",name = "accessKey")
    public AliOssTools getOssTools(){
        AliOssTools ossTools = new AliOssTools();
        ossTools.setOssConfig(toolsProperty.getOss());
        return ossTools.init();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource",name = "driver-class-name")
    public MybatisTools initMybatisTools(){
        SqlSessionFactory sessionFactory = ContextHolder.getBean(SqlSessionFactory.class).get();
        org.apache.ibatis.session.Configuration configuration = sessionFactory.getConfiguration();
        MybatisTools tools = new MybatisTools(configuration);
        ApplicationContext applicationContext = ContextHolder.getApplicationContext();
        Map<String, Object> mapperList = applicationContext.getBeansWithAnnotation(Mapper.class);
        mapperList.forEach((key,value)->{
            if(value instanceof BaseMapper){
                BaseMapper baseMapper = (BaseMapper) value;
                // 初始化一些缓存信息
                baseMapper.getMapperClass();
                // 从BaseMapper中获取真正的类,这时候的target是
                Class<?> target = AOPTools.getTarget(baseMapper);
                tools.injectMapper(target);
            }
        });
        return tools;
    }



}
