package sunshine.sunshineadmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lixiang
 * @date 2020/11/2
 **/
@RestController
public class TestController {

    @GetMapping("check")
    public String t() {
        System.out.println(new Date()+"in");
        return "OK";
    }
}
