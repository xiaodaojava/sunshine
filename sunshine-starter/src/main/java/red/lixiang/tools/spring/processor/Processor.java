package red.lixiang.tools.spring.processor;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lixiang
 * @date 2020/6/27
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Processor {
    /**
     * 这个处理器,是对应的哪个mapper的
     * @return
     */
    Class<?> domainType() ;
}
