package red.lixiang.tools.common.markdown;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

/**
 * @author lixiang
 * @date 2020/5/2
 **/
public class MarkdownTools {

    private static final Parser parser;

    private static final HtmlRenderer htmlRenderer;

    static {
        DataHolder options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(
                        TablesExtension.create(),
                        StrikethroughExtension.create()
                ));
        parser = Parser.builder(options).build();
        htmlRenderer = HtmlRenderer.builder(options).build();
    }

    public static String markdown2Html(String markdown,MarkdownConfig config){
        Document document = parser.parse(markdown);
        String render = htmlRenderer.render(document);
        return render;
    }

    public static void main(String[] args) {
        String markdownText = """
                #hello , 
                > this is a markdown test
                
                ```java
                
                 public class Test{
                 
                 public void test(){
                    System.out.println("Hello Markdown");
                 }  
                 
                 }
                ```
                """;
        System.out.println(markdown2Html(markdownText,new MarkdownConfig()));
    }
}
