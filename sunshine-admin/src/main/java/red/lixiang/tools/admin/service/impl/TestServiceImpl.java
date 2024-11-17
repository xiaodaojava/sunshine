package red.lixiang.tools.admin.service.impl;

import org.springframework.stereotype.Component;
import red.lixiang.tools.admin.aop.AopAction;
import red.lixiang.tools.admin.service.TestService;

@Component
public class TestServiceImpl implements TestService {


    @AopAction
    @Override
    public void hello(){
        // step1 ： 先做个前置校验

        // step2 ： 查一下外域的数据

        // step3 : 组装一下数据

        // step4 : 再查一波数据

        // step5 ： 做点正事

        // step6 ： 不知道干了点啥

        // step7 : 写点关联数据
    }
}
