package red.lixiang.tools.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import red.lixiang.tools.jdk.DoubleTools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2019/12/23
 **/
public class Main {
    public static void main(String[] args) {
        double a = 5.5d;
        double b = 4.28d;
        double sum = DoubleTools.sum(a, b);
        System.out.println(sum);
    }




}
