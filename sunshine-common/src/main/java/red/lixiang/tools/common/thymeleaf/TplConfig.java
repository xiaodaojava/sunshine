package red.lixiang.tools.common.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;

/**
 * Thymeleaf的配置类
 * @Author lixiang
 * @CreateTime 2019-07-23
 **/
public class TplConfig {

    /**
     * 使用静态内部类的方式来实现单例模式
     */
    static class InitTplConfig{
        final static TemplateEngine templateEngine = new TemplateEngine();
        static TemplateEngine getEngine(){
            return templateEngine;
        }
    }

    /**
     * 别的地方想使用templateEngine来处理模板,调用这个方法来获取
     * @return
     */
    public static TemplateEngine getTemplateEngine() {
        final TemplateEngine templateEngine = InitTplConfig.getEngine();
        // 处理txt的
        templateEngine.addTemplateResolver(textTemplateResolver());

        return templateEngine;
    }


    /**
     * 配置模板的位置,前缀,后缀等
     * @return
     */
    private static ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix("/tpl/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

//    public static void main(String[] args) {
//        TemplateEngine engine = getTemplateEngine();
//        final Context ctx = new Context(Locale.CHINA);
//        Map<String, Object> map = new HashMap<>();
//        map.put("key","你好");
//        ctx.setVariables(map);
//        String process = engine.process("xml/hospitalmap.xml", ctx);
//        System.out.println(process);
//    }

}
