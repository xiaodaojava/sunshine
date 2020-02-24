package red.lixiang.tools.spring;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import red.lixiang.tools.base.annotation.EnhanceTool;

import java.lang.reflect.Method;

/**
 * @author lixiang
 * @date 2019/12/11
 **/
@Aspect
@Component
public class EnhanceToolAspect {

    Logger logger = LoggerFactory.getLogger(EnhanceToolAspect.class);

    /**
     * 切面的组成部分之一，告诉程序要对哪些方法进行操作
     * 在本DEMO中直接指定了一个方法，推荐后续用注解的方式去找方法
     * 在现在注解盛行的时代，这样根据方法名去找方法的用处已经不多了
     * 更多的是通过某个注解去找到对应的方法，后续也会有DEMOb出来
     */
    @Pointcut("@annotation(red.lixiang.tools.base.annotation.EnhanceTool)")
    public void toolAnnotation(){}

    /**
     * 对找到的方法进行修改
     * 有@Around，对方法执行前后进行修改
     * 有@Before, 在方法执行前进行修改
     * 有@After,  在方法执行后进行修改
     * 有@AfterReturning， 在方法返回后进行修改
     * 有@AfterThrowing， 在方法抛出异常后修改
     * 常用的是@Around,常用于对方法体加 Try-catch，对方法执行计时，对方法入参，返回做日志记录等等
     */
    @Around("toolAnnotation()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取入参
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature =(MethodSignature) joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        EnhanceTool tool = method.getAnnotation(EnhanceTool.class);
        if(tool.logParam()){
            try {
                logger.info(methodSignature.getName()+" params is {}",JSON.toJSONString(args));
            }catch (Exception e){

            }
        }
        Object proceed = null ;
        if(tool.tryCatch()){
            try{
                proceed = joinPoint.proceed();
            }catch (Exception e){
                logger.error("",e);
                //todo: 这里异常处理还没想好怎么写
            }
        }else {
            proceed = joinPoint.proceed();
        }
        if(tool.logResult() && proceed!=null){
            try {
                logger.info(methodSignature.getName()+"result is {}",JSON.toJSONString(proceed));
            }catch (Exception e){

            }
        }

        return proceed;
    }
}
