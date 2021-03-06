package red.lixiang.tools.common.jianyingpro;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.common.jianyingpro.models.DraftContent;
import red.lixiang.tools.common.jianyingpro.models.Text;
import red.lixiang.tools.jdk.file.FileTools;

import java.util.stream.Collectors;

import static red.lixiang.tools.common.jianyingpro.models.Text.TYPE_SUBTITLE;

public class JianYingTools {

    public static String getSubtitle(String filePath) {
        String s = FileTools.readFromFile(filePath);
        DraftContent draftContent = JSON.parseObject(s, DraftContent.class);
        String subtitle = draftContent.getMaterial()
                .getTexts()
                .stream()
                .filter(x->TYPE_SUBTITLE.equals(x.getType()))
                .map(Text::getContent)
                .collect(Collectors.joining());
        return subtitle;
    }

    public static void main(String[] args) {
        String subtitle = getSubtitle("C:\\Users\\lixiang\\AppData\\Local\\JianyingPro\\User Data\\Projects\\com.lveditor.draft\\3835B2A7-EEB3-4cca-A385-D007FC925815\\draft_content.json");
        System.out.println(subtitle);
    }
}
