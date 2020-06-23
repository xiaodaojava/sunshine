package red.lixiang.tools.common.mybatis;


import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.common.mybatis.model.Page;
import red.lixiang.tools.common.mybatis.model.QC;
import red.lixiang.tools.common.mybatis.model.Sort;
import red.lixiang.tools.common.mybatis.model.SqlField;
import red.lixiang.tools.jdk.ListTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.reflect.ReflectTools;
import red.lixiang.tools.jdk.security.AESTools;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author lixiang
 * @CreateTime 2019-07-15
 **/
public class MapperTools {

    public static boolean securityFlag = false;
    private static final Logger logger = LoggerFactory.getLogger(MapperTools.class);

    private static ConcurrentHashMap<Class<?>, Field[]> reflectFieldCache = new ConcurrentHashMap<>(50);

    private static ConcurrentHashMap<Class<?>,String> tableFieldCache = new ConcurrentHashMap<>(50);

    private static List<String> getSimpleModelFields(Class<?> clazz){
        Field[] allFields = ReflectTools.getAllFields(clazz);
        List<String> stringList = new ArrayList<>();
        for (Field field : allFields) {
            SqlField sqlField = field.getAnnotation(SqlField.class);
            if(sqlField==null){
                continue;
            }
            stringList.add(field.getName());
        }
        return stringList;
    }

    public static String getTableFieldName(Class<?> clazz){

        String tableField = tableFieldCache.computeIfAbsent(clazz, x -> {
            List<String> fields = getSimpleModelFields(clazz);
            String s = fields.stream().map(StringTools::camel2UnderScope).collect(Collectors.joining(","));
            return s;
        });

        return tableField;
    }



    public static <T> SQL richWhereSql(SQL sql, T t) {
        try {
            Class<?> tClass = t.getClass();

            Field[] declaredFields = reflectFieldCache.computeIfAbsent(tClass,x->{
                List<Field> fieldList = new ArrayList<>(50);
                //获取一个类所有的字段
                //当父类为null的时候说明到达了最上层的父类(Object类)

                fieldList.addAll(Arrays.asList(tClass.getDeclaredFields()));
                //得到父类,然后赋给自己
                Class<?> superclass = tClass.getSuperclass();
                fieldList.addAll(Arrays.asList(superclass.getDeclaredFields()));
                return fieldList.toArray(new Field[0]);
            });
            //int fieldCount = 0;
            for (Field field : declaredFields) {

                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(t);

                if(value == null){
                    continue;
                }

                // 判断分页的
                if (value instanceof Page) {
                    Page p = (Page) value;
                    if(p.getPageIndex() !=null && p.getPageSize()!=null){
                        sql.LIMIT(p.getPageSize());
                        sql.OFFSET(p.getStartIndex());
                    }else {
                        //如果没有设置page,则默认取100条
                        sql.LIMIT(100);
                        sql.OFFSET(0);
                    }
                    continue;
                }

                // 加上排序的
                if(value instanceof Sort){
                    Sort s = (Sort) value;
                    sql.ORDER_BY(s.getSortAll());
                    continue;
                }

                // 如果是额外加上的sql
                if("appendWhereSql".equals(fieldName) ){
                    String appendWhereSql  = (String) value;
                    sql.WHERE(appendWhereSql);
                    continue;
                }

                if (field.isAnnotationPresent(QC.class)) {
                    QC qc = field.getAnnotation(QC.class);
                    if(qc.skipRich()){
                        continue;
                    }
                    String valueStr = value.toString();
                    if(qc.security() && securityFlag){
                        String salt = qc.salt();
                        String aes = qc.aes();
                        Field aesField = tClass.getDeclaredField(aes);
                        aesField.setAccessible(true);
                        if(aesField.get(t)==null){
                            KV kv = AESTools.spiltContent(valueStr, salt);
                            field.set(t,kv.getName());

                            if(kv.getValue()!=null){
                                aesField.set(t,kv.getValue());
                            }
                        }

                    }

                    if (qc.likeQuery()) {
                        //如果是like查询
                        if (!StringTools.isBlank(valueStr)) {
                            sql.WHERE(StringTools.camel2UnderScope(fieldName) + " like concat('%',#{" + fieldName + "},'%')");
                        }
                    } else if (qc.listQuery()) {
                        // 如果是list查询
                        List list = (List) value;
                        if (!ListTools.isBlank(list)) {
                            sql.WHERE(StringTools.camel2UnderScope(qc.fieldName()) + " in (" + convertList2Str(list, qc.classType()) + ")");
                        }
                    }else if(qc.biggerRich()){
                        // 如果是大于查询
                        sql.WHERE(StringTools.camel2UnderScope(qc.fieldName()) + " > #{" + fieldName + "}");
                    }else if(qc.smallerRich()){
                        // 如果是小于查询
                        sql.WHERE(StringTools.camel2UnderScope(qc.fieldName()) + " < #{" + fieldName + "}");
                    }else {
                        //只标识了QC, 没有说具体哪种查询,就按=来处理
                        sql.WHERE(StringTools.camel2UnderScope(fieldName) + "= #{" + fieldName + "}");
                    }
                } else {
                    // 默认的值查询
                    sql.WHERE(StringTools.camel2UnderScope(fieldName) + "= #{" + fieldName + "}");
                }

            }
//            if (fieldCount == 0) {
//                throw new Exception("请加上拼接参数");
//            }
        } catch (Exception e) {
            logger.error("rich where sql wrong", e);
        }
        return sql;
    }


    public static <T> SQL richInsertSql(SQL sql, T t) {
        try {
            Class<?> tClass = t.getClass();
            Field[] declaredFields = reflectFieldCache.computeIfAbsent(tClass, x -> ReflectTools.getAllFields(tClass));
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(t);
                if(!field.isAnnotationPresent(SqlField.class)){
                    continue;
                }
                if(Objects.isNull(value)){
                    continue;
                }
                if (field.isAnnotationPresent(QC.class)) {
                    QC qc = field.getAnnotation(QC.class);
                    if (qc.skipRich()) {
                        continue;
                    }
                }
                SqlField sqlField = field.getAnnotation(SqlField.class);
                if(sqlField.security() && securityFlag){
                    //需要加密特殊处理的
                    String aesFieldName = sqlField.aes();
                    String saltFieldName = sqlField.salt();
                    String s = value.toString();
                    //把整个s划分成三段,只对中间一段加密
                    KV kv = AESTools.spiltContent(s, saltFieldName);
                    field.set(t,kv.getName());
                    Field aesField = tClass.getDeclaredField(aesFieldName);
                    aesField.setAccessible(true);
                    if(kv.getValue()!=null){
                        aesField.set(t,kv.getValue());
                    }
                }
                // 如果值不为空的话，往里面拼接insertSQL
                sql.VALUES(StringTools.camel2UnderScope(fieldName), String.format("#{%s}", fieldName));
            }
        } catch (Exception e) {
            logger.error("执行查询sql有错", e);
        }
        return sql;

    }

    /**
     * 更新的时候都用id来更新
     *
     * @param sql
     * @param t
     * @param <T>
     * @return
     */
    public static <T> SQL richUpdate(SQL sql, T t) {
        try {
            Class<?> tClass = t.getClass();
            Field[] declaredFields = reflectFieldCache.computeIfAbsent(tClass, x -> ReflectTools.getAllFields(tClass));
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(t);
                if(!field.isAnnotationPresent(SqlField.class)){
                    continue;
                }
                if(Objects.isNull(value)){
                    continue;
                }
                if (field.isAnnotationPresent(QC.class)) {
                    QC qc = field.getAnnotation(QC.class);
                    if (qc.skipRich()) {
                        continue;
                    }
                }
                SqlField sqlField = field.getAnnotation(SqlField.class);
                if(sqlField.security() && securityFlag){
                    //需要加密特殊处理的
                    String aesFieldName = sqlField.aes();
                    Field aesField = tClass.getDeclaredField(aesFieldName);
                    aesField.setAccessible(true);
//                    if(aesField.get(t)==null){
                        String saltFieldName = sqlField.salt();
                        String s = value.toString();
                        //把整个s划分成三段,只对中间一段加密
                        KV kv = AESTools.spiltContent(s, saltFieldName);
                        field.set(t,kv.getName());

                        if(kv.getValue()!=null){
                            aesField.set(t,kv.getValue());
                        }
//                    }

                }
                // 如果值不为空的话，往里面拼接updateSQL
                sql.SET(StringTools.camel2UnderScope(fieldName) + "=" + String.format("#{%s}", fieldName));

            }
        } catch (Exception e) {
            logger.error("执行更新sql有错", e);
        }
        return sql;
    }


    /**
     * 把一个 list 转成in 的用 , 拼接的字符串
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String convertList2Str(List<T> list) {

        return convertList2Str(list, String.class);
    }

    public static <T> String convertList2Str(List<T> list, Class<?> clazz) {

        if (clazz.equals(Long.class) || clazz.equals(Integer.class)) {
            String idStr = list.stream().map(String::valueOf).collect(Collectors.joining(","));
            return idStr;
        }
        String idStr = list.stream().map(String::valueOf).collect(Collectors.joining("','"));
        return "'" + idStr + "'";

    }

    private boolean baseType(Class<?> clazz) {
        // 这里面只列举了一些常用的，时间类型的还没有测试
        return clazz == String.class || clazz == int.class || clazz == long.class
                || clazz == BigDecimal.class;
    }

    public static <T> T getOne(List<T> tList) {
        if (null != tList && tList.isEmpty()) {
            return null;
        }
        if (null != tList && tList.size() == 1) {
            return tList.get(0);
        }
        throw new RuntimeException("too many result");
    }


}
