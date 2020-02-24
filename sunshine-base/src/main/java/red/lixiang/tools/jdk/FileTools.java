package red.lixiang.tools.jdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author lixiang
 * @CreateTime 2019-08-02
 **/
public class FileTools {

    /**
     * 获取文件的后缀名
     * @param fileName
     * @return
     */
    public  static String getSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public  static String getNameWithoutSuffix(String fileName){
        return fileName.substring(0,fileName.lastIndexOf("."));
    }




    public static void main(String[] args) {
        String suffixName = getNameWithoutSuffix("aa.jpg");
        System.out.println(suffixName);
    }
}
