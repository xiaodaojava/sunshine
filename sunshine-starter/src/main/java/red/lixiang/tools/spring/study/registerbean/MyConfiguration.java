package red.lixiang.tools.spring.study.registerbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixiang
 * @date 2020/8/13
 **/
@Configuration
public class MyConfiguration {

    @Bean
    public CustomerRegister customerRegister(){
        return new CustomerRegister();
    }
}
