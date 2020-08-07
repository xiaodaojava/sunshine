package red.lixiang.tools.spring.mybatis;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.common.mybatis.MapperTools;
import red.lixiang.tools.common.mybatis.model.QC;
import red.lixiang.tools.jdk.ListTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.reflect.ReflectTools;
import red.lixiang.tools.jdk.security.AESTools;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author lixiang
 * @date 2020/6/27
 **/
public class MybatisTools {

    private MapperBuilderAssistant builderAssistant;

    private Configuration configuration;


    private SqlSource sqlSource;


    public MybatisTools(Configuration configuration) {
        this.configuration = configuration;
    }

    public void injectMapper(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(PlusSql.class)) {
                continue;
            }
            String methodName = method.getName();
            String msId = cls.getName() + "." + methodName;
            Type genericReturnType = method.getGenericReturnType();
            Type returnType = genericReturnType;
            if(genericReturnType instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType)genericReturnType;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                returnType = actualTypeArgument;
            }
            PlusSql plusSql = method.getAnnotation(PlusSql.class);
            if (plusSql.sqlType().equals("select")) {
                String[] params = plusSql.whereParam();
                // 可以直接获取方法的参数列表
//                Parameter[] parameters = method.getParameters();
                SQL sql = new SQL() {
                    {
                        SELECT("*");
                        FROM(MapperTools.tableNameFromCls(cls));
                    }
                };
                for (String param : params) {

                    sql.WHERE(StringTools.camel2UnderScope(param) + " = " + "#{" + param + "}");

                }
                SqlSource sqlSource = new RawSqlSource(configuration, sql.toString(), (Class<?>) returnType);
                addSelectMappedStatement(msId, sqlSource,(Class<?>)returnType);
            }
            if (plusSql.sqlType().equals("delete")) {
                String[] params = plusSql.whereParam();
                SQL sql = new SQL() {
                    {
                        DELETE_FROM(MapperTools.tableNameFromCls(cls));
                        for (String param : params) {
                            WHERE(StringTools.camel2UnderScope(param) + " = #{" + param + "}");
                        }
                    }
                };
                SqlSource sqlSource = new RawSqlSource(configuration, sql.toString(), Object.class);
                addUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            }

        }
    }

    /**
     * 创建一个查询的MS
     *
     * @param msId       类名.方法名: com.red.lixiang.dao.PassportDAO.findByMobile(String mobile)
     * @param sqlSource  执行的sqlSource
     * @param resultType 返回的结果类型
     */
    private void addSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {

        ResultMap inlineResultMap = new ResultMap.Builder(
                configuration,
                msId + "-Inline",
                resultType,
                new ArrayList<>(),
                null).build();

        MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, SqlCommandType.SELECT)
                .resultMaps(Collections.singletonList(inlineResultMap))
                .build();
        //缓存
        configuration.addMappedStatement(ms);
    }

    /**
     * 创建一个简单的MS
     * 可以执行,新增/更新/删除操作用
     *
     * @param msId
     * @param sqlSource      执行的sqlSource
     * @param sqlCommandType 执行的sqlCommandType
     */
    private void addUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType) {
        ResultMap inlineResultMap = new ResultMap.Builder(
                configuration,
                msId + "-Inline",
                Long.class,
                new ArrayList<>(),
                null).build();
        MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, sqlCommandType)
                .resultMaps(Collections.singletonList(inlineResultMap))
                .build();
        //缓存
        configuration.addMappedStatement(ms);
    }
//    public MybatisTools init(Class<?> mapperCls){
//        String resource = mapperCls.getName().replace('.', '/') + ".java (best guess)";
//        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
//        builderAssistant.setCurrentNamespace(mapperCls.getName());
//        sqlSource = new RawSqlSource(configuration, "select * from passport where mobile = #{mobile}", Object.class);
//        return this;
//    }
//
//    public void injectPassportMapper(){
//        addSelectMappedStatementForOther(PassportMapper.class,"findByMobile",sqlSource,Object.class);
//    }
//

    /**
     * 查询的mappedStatement
     *
     * @param mapperClass Mapper的类
     * @param id          方法名
     * @param sqlSource
     * @param resultType  返回值类型
     * @return
     */
    protected MappedStatement addSelectMappedStatementForOther(Class<?> mapperClass, String id, SqlSource sqlSource,
                                                               Class<?> resultType) {
        return addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.SELECT, null,
                null, resultType, new NoKeyGenerator(), null, null);
    }

    /**
     * 添加 MappedStatement 到 Mybatis 容器
     */
    protected MappedStatement addMappedStatement(Class<?> mapperClass, String id, SqlSource sqlSource,
                                                 SqlCommandType sqlCommandType, Class<?> parameterType,
                                                 String resultMap, Class<?> resultType, KeyGenerator keyGenerator,
                                                 String keyProperty, String keyColumn) {
        String statementName = mapperClass.getName() + "." + id;

        /* 缓存逻辑处理 */
        boolean isSelect = false;
        if (sqlCommandType == SqlCommandType.SELECT) {
            isSelect = true;
        }
        return builderAssistant.addMappedStatement(statementName, sqlSource, StatementType.PREPARED, sqlCommandType,
                null, null, null, parameterType, resultMap, resultType,
                null, !isSelect, isSelect, false, keyGenerator, keyProperty, keyColumn,
                configuration.getDatabaseId(), configuration.getDefaultScriptingLanguageInstance(), null);
    }
}
