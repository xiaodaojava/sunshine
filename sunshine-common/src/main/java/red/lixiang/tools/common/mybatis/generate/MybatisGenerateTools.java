package red.lixiang.tools.common.mybatis.generate;


import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.common.thymeleaf.TplConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import red.lixiang.tools.jdk.domain.DomainDesc;
import red.lixiang.tools.jdk.sql.model.SqlField;
import red.lixiang.tools.jdk.sql.model.SqlTable;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.*;

import static red.lixiang.tools.common.mybatis.generate.DomainDescTemplates.*;

/**
 * @Author lixiang
 * @CreateTime 24/03/2018
 **/
public class MybatisGenerateTools {







    public static DomainDesc getDomainDescFromTable(SqlTable tableInfo,String packageName){
        //初始化thymeleaf
        TemplateEngine engine = TplConfig.getTemplateEngine();
        final Context ctx = new Context(Locale.CHINA);
        Map<String, Object> map = new HashMap<>(1);
        map.put("table",tableInfo);
        ctx.setVariables(map);
        ctx.setVariable("basePackage",packageName);
        // 默认的领域的名称就是表的名字全小写
        ctx.setVariable("domainName",tableInfo.getJavaTableName().toLowerCase());
        String dbModel = engine.process(DB_MODEL, ctx);
        String qc = engine.process(QC, ctx);
        String javaMapper = engine.process(JAVA_MAPPER, ctx);
        String xmlMapper = engine.process(XML_MAPPER, ctx);
        String provider = engine.process(PROVIDER, ctx);
        String manager = engine.process(MANAGER, ctx);
        String managerImpl = engine.process(MANAGER_IMPL, ctx);
        String controller = engine.process(CONTROLLER, ctx);
        DomainDesc domainDesc = new DomainDesc();
        domainDesc.setDbModel(dbModel)
                .setModelName(tableInfo.getJavaTableName())
                .setQc(qc)
                .setController(controller)
                .setManager(manager)
                .setManagerImpl(managerImpl)
                .setProvider(provider)
                .setXmlMapper(xmlMapper)
                .setJavaMapper(javaMapper);
        return domainDesc;
    }

}
