package red.lixiang.tools.spring.convert;

import red.lixiang.tools.base.annotation.EnhanceTool;
import red.lixiang.tools.common.convertor.Convertor;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.common.mybatis.MapperTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.spring.ContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/25
 **/
public class ConvertorTools {

    /**
     * 实例缓存
     */
    public static Map<Class<?>, Convertor> CONVERTOR_CACHE = new ConcurrentHashMap<>();

    public static  <T> void convertSingle(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(EnhanceTool.class)) {
                    continue;
                }
                EnhanceTool enhance = field.getAnnotation(EnhanceTool.class);
                String sourceFieldName = enhance.source();
                Field sourceField = null;
                sourceField = t.getClass().getDeclaredField(sourceFieldName);

                if (!sourceField.canAccess(t)) {
                    sourceField.setAccessible(true);
                }
                Object sourceObj = sourceField.get(t);
                if (sourceObj == null) {
                    continue;
                }
                if (StringTools.isNotBlank(sourceFieldName) && enhance.convertor() != Object.class) {
                    // 走convert的方式
                    Convertor convertor = CONVERTOR_CACHE.computeIfAbsent(enhance.convertor(), x -> {
                        try {
                            return (Convertor) x.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });

                    Object value = convertor.convertSingle(sourceField.get(t),enhance.targetIdentity());
                    field.set(t, value);

                }
                if (StringTools.isNotBlank(enhance.targetField()) && enhance.targetEntity() != Object.class) {
                    // 走默认SQL的方式
                    Class<?> targetEntity = enhance.targetEntity();
                    String tableName = MapperTools.tableNameFromCls(targetEntity);
                    StringBuilder sqlBuilder = new StringBuilder("select ");
                    sqlBuilder.append(enhance.targetField())
                            .append(" from ").append(tableName)
                            .append(" where ").append(enhance.targetIdentity())
                            .append(" = ").append(sourceObj);
                    BaseMapper baseMapper = (BaseMapper) ContextHolder.getApplicationContext().getBean(tableName + "Mapper");
                    Object o = baseMapper.selectOne(sqlBuilder.toString());
                    Field targetObjField = o.getClass().getField(enhance.targetField());
                    targetObjField.setAccessible(true);
                    field.set(t, targetObjField.get(o));
                }
            }

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param list
     * @param cls  当前List里面的实体类型
     * @param <T>
     */
    public static  <T> void convertList(List<T> list, Class<T> cls) {
        //先获取到有哪些字段需要注入
        Field[] fields = cls.getDeclaredFields();
        // 记录下使用Convertor转化的那些 field-List<Long> idList;
        Map<Field, HashSet<Object>> convertorMap = new HashMap<>();
        Map<Field, HashSet<Object>> targetMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(EnhanceTool.class)) {
                continue;
            }
            EnhanceTool enhance = field.getAnnotation(EnhanceTool.class);
            Class<?> convertorClass = enhance.convertor();
            String sourceFieldName = enhance.source();
            if (StringTools.isBlank(sourceFieldName) && StringTools.isBlank(enhance.targetField())) {
                continue;
            }
            HashSet<Object> set = new HashSet<>();
            for (T t : list) {
                try {
                    Object o = field.get(t);
                    set.add(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (convertorClass != Object.class && StringTools.isNotBlank(sourceFieldName)) {
                // 这个字段需要注入的
                // 从list中获取这些字段的值
                convertorMap.put(field, set);
            }
            if (StringTools.isNotBlank(enhance.targetField())) {
                targetMap.put(field, set);
            }

        }

        // 去找对应的convert进行转化
        convertListWithConvertor(list, convertorMap, cls);

        // 使用对应的sql转换器去转化
        convertListWithSql(list, targetMap, cls);

    }

    static <T> void convertListWithSql(List<T> list, Map<Field, HashSet<Object>> convertorMap, Class<T> cls) {
        if (convertorMap.isEmpty()) {
            return;
        }
        Map<Field,Map<Object,Object>> valueMap = new HashMap<>();
        convertorMap.forEach((key, value) -> {
            EnhanceTool enhanceTool = key.getAnnotation(EnhanceTool.class);
            List<Object> objectList = new ArrayList<>(value);
            Class<?> targetClass = enhanceTool.targetEntity();
            String targetField = enhanceTool.targetField();
            String targetIdentity = enhanceTool.targetIdentity();
            String tableName = MapperTools.tableNameFromCls(targetClass);
            StringBuilder sqlBuilder = new StringBuilder("select ")
                    .append(targetField).append(" , ").append(targetIdentity)
                    .append(" from ").append(tableName).append(" where '")
                    .append(targetIdentity).append("' in (").append(MapperTools.convertList2Str(objectList))
                    .append(")");
            BaseMapper baseMapper = (BaseMapper) ContextHolder.getApplicationContext().getBean(tableName + "Mapper");
            List selectList = baseMapper.selectList(sqlBuilder.toString());
            // 把这个list变成map的形式, key是targetIdentity , value是targetField
            Map<Object, Object> pairMap = new HashMap<>();
            for (Object o : selectList) {
                Class<?> aClass = o.getClass();
                try {
                    Field field = aClass.getField(targetField);
                    Field identityField = aClass.getField(targetIdentity);
                    field.setAccessible(true);
                    identityField.setAccessible(true);
                    pairMap.put(identityField.get(o), field.get(o));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            valueMap.put(key,pairMap);

        });
        for (T t : list) {
          valueMap.forEach((key,map)->{
              try {
                  EnhanceTool enhanceTool = key.getAnnotation(EnhanceTool.class);
                  Field sourceField = cls.getField(enhanceTool.source());
                  if (!sourceField.canAccess(t)) {
                      sourceField.setAccessible(true);
                  }
                  Object o = map.get(sourceField);
                  key.set(t, o);
              } catch (NoSuchFieldException | IllegalAccessException e) {
                  e.printStackTrace();
              }
          });
        }


    }

    static <T> void convertListWithConvertor(List<T> list, Map<Field, HashSet<Object>> convertorMap, Class<T> cls) {
        if (convertorMap.isEmpty()) {
            return;
        }
        Map<Field,Map<Object,Object>> valueMap = new HashMap<>();
        //通过convert去取值
        convertorMap.forEach((key, value) -> {
            EnhanceTool enhanceTool = key.getAnnotation(EnhanceTool.class);
            Convertor convertor = CONVERTOR_CACHE.computeIfAbsent(enhanceTool.convertor(), x -> {
                try {
                    return (Convertor) x.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            });
            List<Object> valueList = new ArrayList<>(value);
            Map<Object, Object> map = convertor.convertList(valueList,enhanceTool.targetIdentity());
            valueMap.put(key,map);
        });

        // 取完值之后, 去充填list
        for (T t : list) {
            valueMap.forEach((key,map)->{
                try {
                    EnhanceTool enhanceTool = key.getAnnotation(EnhanceTool.class);
                    Field sourceField = cls.getDeclaredField(enhanceTool.source());
                    if (!sourceField.canAccess(t)) {
                        sourceField.setAccessible(true);
                    }
                    Object sourceValue = sourceField.get(t);
                    Object o = map.get(String.valueOf(sourceValue));
                    key.set(t, o);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

        }
    }


}
