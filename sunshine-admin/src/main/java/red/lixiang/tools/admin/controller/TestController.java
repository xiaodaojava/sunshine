package red.lixiang.tools.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import red.lixiang.tools.admin.aop.AopAction;
import red.lixiang.tools.admin.service.TestService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lixiang
 * @date 2020/11/2
 **/
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("check")
    public String t() {
        testService.hello();
        System.out.println(new Date()+"in");
        return "OK";
    }
}
