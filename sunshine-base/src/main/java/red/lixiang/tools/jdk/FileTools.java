package red.lixiang.tools.jdk;

import red.lixiang.tools.jdk.io.IOTools;
import red.lixiang.tools.jdk.os.OSTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.stream.Stream;

/**
 * @Author lixiang
 * @CreateTime 2019-08-02
 **/
public class FileTools {

    /**
     * 获取文件的后缀名,包含 '.'
     * @param fileName
     * @return
     */
    public  static String getSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getFileNameFromPath(String path){
        String separator = OSTools.fileSeparator();
        if(!path.contains(separator)){
            return path;
        }else {
            int i = path.lastIndexOf(separator);
            path = path.substring(i+1);
            return path;
        }
    }



    public  static String getSuffixNameNoDot(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    public  static String getNameWithoutSuffix(String fileName){
        return fileName.substring(0,fileName.lastIndexOf("."));
    }

    public static String fileBase64Content(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = IOTools.readByte(inputStream);
            inputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String fileBase64Content(String path){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteDirFiles(String path){
        try {
            Stream<Path> list = Files.list(Paths.get(path));
            list.forEach(x->{
                try {
                    Files.deleteIfExists(x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String suffixName = getSuffixNameNoDot("aa.jpg");
        System.out.println(suffixName);
    }
}
