package red.lixiang.tools.common.jianyingpro.test;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.common.jianyingpro.models.DraftContent;
import red.lixiang.tools.common.jianyingpro.models.Material;
import red.lixiang.tools.jdk.file.FileTools;
import red.lixiang.tools.jdk.io.IOTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestMain {

    public static void main(String[] args) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\lixiang\\AppData\\Local\\JianyingPro\\User Data\\Projects\\com.lveditor.draft\\3E22BFC5-6DAB-4ad2-9E94-26A0DB79ECD1\\draft_content.json"));
            String s = new String(bytes);
            DraftContent draftContent = JSON.parseObject(s, DraftContent.class);
            System.out.println(draftContent);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
