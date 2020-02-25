package red.lixiang.tools.common.mybatis.plugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import red.lixiang.tools.common.mybatis.model.SqlField;
import red.lixiang.tools.jdk.security.AESTools;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author lixiang
 * @date 2020/1/8
 **/
@Intercepts({@Signature(type = ResultSetHandler.class ,method = "handleResultSets",args ={Statement.class} )})
public class SecurityInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        List list = (List) proceed;
        for (Object o : list) {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(o);
                if(Objects.isNull(value)){
                    continue;
                }
                if(field.isAnnotationPresent(SqlField.class)){
                    SqlField sqlField = field.getAnnotation(SqlField.class);
                    if(sqlField.security()){
                        String aesFieldName = sqlField.aes();
                        String saltFieldName = sqlField.salt();
                        Field aesField = o.getClass().getDeclaredField(aesFieldName);
                        aesField.setAccessible(true);
                        Object obj = aesField.get(o);
                        if(obj == null){
                            continue;
                        }
                        String aesStr = obj.toString();
                        String result = AESTools.revertContent(value.toString(), aesStr, saltFieldName);
                        field.set(o,result);
                    }
                }

            }
        }
        return list;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties.toString());
    }
}
