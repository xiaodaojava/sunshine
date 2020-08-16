package red.lixiang.tools.spring.study.registerbean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lixiang
 * @date 2020/8/13
 **/
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        MyBean hello = (MyBean)context.getBean("hello");
        hello.test();
    }
}
