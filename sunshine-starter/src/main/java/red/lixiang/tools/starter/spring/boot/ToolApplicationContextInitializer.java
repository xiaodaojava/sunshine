package red.lixiang.tools.starter.spring.boot;

import red.lixiang.tools.spring.ContextHolder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;

import java.util.Iterator;

/**
 * @Author lixiang
 * @CreateTime 2019-07-24
 **/
public class ToolApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        //这里注入一个applicationContext , 然后别的程序就可以直接调用Context了
        ContextHolder.setApplicationContext(applicationContext);


        Iterator<PropertySource<?>> iterator = applicationContext.getEnvironment().getPropertySources().iterator();

        //boolean b = check.checkAll();

        System.out.println("欢迎使用sunshine基础工具包");
        System.out.println("Do you like sunshine? yes, I love");
        System.out.println("在使用过程中,如有问题,请联系开发者,微信:best396975802");
    }


}
