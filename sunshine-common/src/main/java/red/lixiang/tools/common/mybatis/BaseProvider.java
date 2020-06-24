package red.lixiang.tools.common.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import red.lixiang.tools.common.mybatis.model.BaseQC;
import red.lixiang.tools.jdk.StringTools;

import static red.lixiang.tools.common.mybatis.MybatisToolCache.MAPPER_TABLE_CACHE;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public class BaseProvider implements ProviderMethodResolver {

    /**
     * 查找全部的
     * @see BaseMapper#findAll(Class)
     * @param clazz
     * @return
     */
    public String findAll(Class<?> clazz){
        SQL sql = new SQL() {{
            SELECT("*");
        }};
        sql.FROM(tableNameFromCls(clazz));
        return sql.toString();
    }

    /**
     * @see BaseMapper#findById(Long, Class)
     * @param id
     * @param clazz
     * @return
     */
    public String findById(@Param("id") Long id, Class<?> clazz){
        SQL sql = new SQL() {{
            SELECT("*");
            WHERE("id = #{id}");
        }};
        sql.FROM(tableNameFromCls(clazz));
        return sql.toString();
    }

    /**
     * @see BaseMapper#findByQuery(BaseQC)
     * @param qc
     * @return
     */
    public String findByQuery(BaseQC qc){
        SQL sql = new SQL() {{
            SELECT("*");
        }};
        MapperTools.richWhereSql(sql,qc);
        sql.FROM(tableNameFromObj(qc));
        return sql.toString();
    }

    /**
     * @see BaseMapper#countByQuery(BaseQC) (BaseQC)
     * @param qc
     * @return
     */
    public String countByQuery(BaseQC qc){
        SQL sql = new SQL() {{
            SELECT("count(1)");
        }};
        MapperTools.richWhereSql(sql,qc);
        sql.FROM(tableNameFromObj(qc));
        return sql.toString();
    }

    /**
     * @see BaseMapper#insert(Object)
     * @param t
     * @return
     */
    public String insert(Object t){
        SQL sql = new SQL() {{
            INSERT_INTO(tableNameFromObj(t));
        }};
        MapperTools.richInsertSql(sql,t);

        return sql.toString();
    }

    /**
     * @see BaseMapper#update(Object)
     * @param t
     * @return
     */
    public String update(Object t) {
        SQL sql = new SQL() {{
            UPDATE(tableNameFromObj(t));

        }};
        MapperTools.richUpdate(sql, t);
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    /**
     * @see BaseMapper#removeById(Long, Class)
     * @param id
     * @param clazz
     * @return
     */
    public String removeById(@Param("id") long id,Class<?> clazz){
        SQL sql = new SQL() {{
            DELETE_FROM(tableNameFromCls(clazz));
        }};
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    /**
     * @see BaseMapper#removeByQuery(BaseQC)
     * @param qc
     * @return
     */
    public String removeByQuery(BaseQC qc){
        SQL sql = new SQL() {{
            DELETE_FROM(tableNameFromObj(qc));
        }};
        MapperTools.richWhereSql(sql,qc);
        return sql.toString();
    }



    private String tableNameFromObj(Object qc){
        return tableNameFromCls(qc.getClass());
    }

    private String tableNameFromCls(Class<?> clazz){
        return MAPPER_TABLE_CACHE.computeIfAbsent(clazz,cls->{
            String simpleName = cls.getSimpleName();
            String s = simpleName.replaceAll("Mapper", "");
            s = s.replaceAll("DAO", "");
            s = s.replaceAll("QC", "");
            s = s.replaceAll("DO", "");
            return StringTools.camel2UnderScope(s);});
    }

}
