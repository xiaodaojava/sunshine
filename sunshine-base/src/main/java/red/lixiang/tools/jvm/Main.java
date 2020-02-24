package red.lixiang.tools.jvm;

/**
 * @author lixiang
 * @date 2020/1/20
 **/
public class Main {


    public static void main(String[] args) {
        String s1 = "java技术大本营";
        String s2 = "java技术大本营";
        String s3 = "凑"+"心";
        String s4 = new String("凑心");
        String s5 = new String("Drift north");
        //true
        System.out.println(s1==s2);
        // false
        System.out.println(s3==s4);
        String s6 = s4.intern();
        // true
        System.out.println(s3==s6);
    }
}
