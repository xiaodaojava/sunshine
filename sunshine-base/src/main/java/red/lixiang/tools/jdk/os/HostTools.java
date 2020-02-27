package red.lixiang.tools.jdk.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author lixiang
 * @date 2020/2/26
 **/
public class HostTools {
    public  static String getHostPath(){
        String path  = "/etc/hosts";
        if(OSTools.isWin()){
            path = "c:\\windows\\system32\\drivers\\etc\\hosts";
        }
        return path;
    }

    public static String loadHost(){
        String path = getHostPath();
        String result;
        try {
            result = Files.readString(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "read host fail"+e.getMessage();
        }
        return result;
    }
    public static String  saveHost(String content){
        String path = getHostPath();
        if (!Files.isWritable(Paths.get(path))) {
            return "fail, file can not write!";
        }
        try {
            Files.writeString(Paths.get(path),content);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "write fail:"+e.getMessage();
        }
    }

    public static void main(String[] args) {
        String s = loadHost();
        System.out.println(s);
//        s+="\n 127.0.0.1 a.b.c";
//        String s1 = saveHost(s);
//        System.out.println(s1);
    }
}
