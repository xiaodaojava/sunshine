package red.lixiang.tools.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixiang
 * @date 2020/1/8
 **/
public class RegularTools {


    public static void main(String[] args) {
        //强密码验证
//        String p  = "^(?=.{6,16})([0-9A-Za-z]*[0-9A-Za-z][0-9A-Za-z]*)+$";
//        String p  = "^.*(?=.{6,16})(0-9)*(A-Z)*(a-z)*(?=.*[!@#$%^&*?()])*$";
        // (?![A-Z0-9\\W]+$)(?![a-z0-9\\W]+$)
         String p = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[{?:;'\"\\[\\]<>}!@#$%^&*_+=\\-,\\\\.])[0-9a-zA-Z{?:;'\"\\[\\]<>}!@#$%^&*_+=\\-,\\\\.]{6,}$";

        String pass = "123123";
        System.out.println(pass.matches(p));
        String p1 = "12123asdv";
        System.out.println(p1.matches(p));

        String p2 = "12312AWdwqr23";
        System.out.println(p2.matches(p));

        String p3 = "!@#$%^&*";
        System.out.println(p3.matches(p));

        String p4 = "12Aa123#$-{}?[]]<>";
        System.out.println(p4.matches(p));

        String p5 = "Aa123#$-{}?[]]<>";
        System.out.println(p5.matches(p));


    }
}
