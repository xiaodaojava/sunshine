package red.lixiang.tools.jdk;

import red.lixiang.tools.jdk.os.OSTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * 这个类是记录日志的,对日志做一个封装
 *
 * @author lixiang
 * @date 2020/3/27
 **/
public class ToolsLogger {

    public static final Logger LOGGER = Logger.getLogger("Tools");

    static FileHandler fileHandler= null;

    public static boolean debug(){
        return LOGGER.isLoggable(Level.CONFIG);
    }

    public static void init(String path){
        String defaultPath = OSTools.userHomePath()+"/.tools/";
        if(Files.notExists(Paths.get(defaultPath))){
            try {
                Files.createDirectories(Paths.get(defaultPath));
            } catch (IOException e) {
            }
        }
        path = StringTools.isBlank(path)?defaultPath+"tools.log":path;
        //对logger做一些配置
        try {
            fileHandler = new FileHandler(path,true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }

    static {
        init(null);
    }

    public static void plainInfo(String content, Object... objects){
        content = content.replaceAll("\\{}", "%s");
        content = String.format(content,objects);
        System.out.println(content);
    }

    public static void info(String content, Object... objects){
        content = content.replaceAll("\\{}", "%s");
        content = String.format(content,objects);
        LOGGER.info(content);
    }

    public static void error(String msg, Throwable t){
        LOGGER.severe(msg+" "+t.getMessage());
    }

    public static void error(String msg){
        LOGGER.severe(msg);
    }

    public static void out(Object... objects){
        String s = Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(","));
        System.out.println(s);
    }



}
