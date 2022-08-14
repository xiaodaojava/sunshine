package red.lixiang.tools.admin.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    @Pointcut("@annotation(red.lixiang.tools.admin.aop.AopAction)")
    public void annotationPointcut(){};

    @Before("annotationPointcut()")
    public void doBefore(){
        System.out.println("before");
    }

    @After("annotationPointcut()")
    public void doAfter(){
        System.out.println("after");
    }


}
