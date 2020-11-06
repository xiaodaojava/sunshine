package red.lixiang.tools.jdk;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author lixiang
 * @date 2020/11/6
 **/
public class ExceptionTools {


    public static void main(String[] args) {
        try {
            int i =1/0;
            System.out.println(i);
        }catch (Exception e){
            String s = exceptionMsg(e);
            System.out.println(s);
        }
    }



    public static  String exceptionMsg(Throwable e){
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.toString();
    }

}
