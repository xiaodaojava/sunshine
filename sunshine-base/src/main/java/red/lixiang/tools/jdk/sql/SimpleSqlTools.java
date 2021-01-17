package red.lixiang.tools.jdk.sql;

import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.reflect.ReflectTools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/10/29
 **/
public class SimpleSqlTools {

    public  static  List<Map> selectList(String sql,SqlConfig config){
        return selectList(sql,Map.class,config.getTargetDb(),config.conn());
    }

    public  static <T> List<T> selectList(String sql, Class<T> clazz,SqlConfig config){
        return selectList(sql,clazz,config.getTargetDb(),config.conn());
    }

    public  static <T> List<T> selectList(String sql, Class<T> clazz, String targetDb,Connection connection){
        List<Map<String, Object>> resultList = SqlTools.tableDetail(sql, targetDb,connection);
        if(clazz==Map.class){
            return (List<T>) resultList;
        }
        List<T> list = new ArrayList<>();
        Field[] allFields = ReflectTools.getAllFields(clazz);
        for (Map<String, Object> map : resultList) {
            try {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (Field field : allFields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object o = map.get(StringTools.camel2UnderScope(name));
                    if(o!=null){
                        field.set(t,o);
                    }
                }
                list.add(t);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public static void main(String[] args) {

    }

}
