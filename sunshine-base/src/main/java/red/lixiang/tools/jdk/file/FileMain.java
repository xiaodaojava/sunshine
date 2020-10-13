package red.lixiang.tools.jdk.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lixiang
 * @date 2020/10/7
 **/
public class FileMain {
    public static void main(String[] args) {
        List<Path> strings = FileTools.listFile("/Volumes/ship/video/sexr");
        for (Path p : strings) {
            String s = p.getFileName().toString();
            if(s.endsWith(".mp4")){
                try {
                    Files.move(p, Path.of(p.getParent().getParent() + "/" + s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
