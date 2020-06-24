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
        String model = engine.process("text/mybatis/mysql-model.txt", ctx);
        String qc = engine.process("text/mybatis/mysql-qc.txt", ctx);
        String dao = engine.process("text/mybatis/mysql-dao-java.txt", ctx);
        String provider = engine.process("text/mybatis/mysql-provider.txt", ctx);
        String manager = engine.process("text/mybatis/mysql-manager-java.txt", ctx);
        String managerImpl = engine.process("text/mybatis/mysql-manager-impl-java.txt", ctx);
        String controller = engine.process("text/mybatis/mysql-controller-java.txt", ctx);
        DomainDesc domainDesc = new DomainDesc();
        domainDesc.setDbModel(model)
                .setModelName(tableInfo.getJavaTableName())
                .setQc(qc)
                .setController(controller)
                .setManager(manager)
                .setManagerImpl(managerImpl)
                .setProvider(provider)
//                .setXmlMapper(xmlMapper)
                .setJavaMapper(dao);
        return domainDesc;
    }

}
