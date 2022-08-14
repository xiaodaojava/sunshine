package red.lixiang.tools.admin.service.impl;

import org.springframework.stereotype.Component;
import red.lixiang.tools.admin.aop.AopAction;
import red.lixiang.tools.admin.service.TestService;

@Component
public class TestServiceImpl implements TestService {


    @AopAction
    @Override
    public void hello(){
        System.out.println("in service");
    }
}
