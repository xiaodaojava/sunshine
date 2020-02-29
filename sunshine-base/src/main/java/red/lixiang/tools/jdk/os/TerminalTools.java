package red.lixiang.tools.jdk.os;


import red.lixiang.tools.jdk.ArrayTools;
import red.lixiang.tools.jdk.io.IOTools;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author lixiang
 * @date 2020/2/29
 **/
public class TerminalTools {

    /**
     * 可以执行cmd命令
     * 执行root命令的传参示例
     *  String[] cmd = {"/bin/bash","-c","echo \"xxdxxdxxd\"| sudo -S echo bbb>a.txt"};
     *  Process pb = Runtime.getRuntime().exec(cmd);
     * @param command
     * @param options
     * @return
     */
    public static String exec(String command,String... options){
        String[] cmd  =  ArrayTools.concatAll(new String[]{command},options);
        try {
            Process pb = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String s = IOTools.readStringFromReader(input);
            input.close();
            return s;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public static String execByRoot(String command,String root){
        StringBuilder sb = new StringBuilder();
        sb.append("echo  \"").append(root).append("\"|sudo -S ").append(command);
        return exec("/bin/bash","-c",sb.toString());
    }


}
