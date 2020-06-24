package red.lixiang.tools.jdk.reflect;


import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lixiang
 * @date 2019/12/12
 **/
public class ReflectTools {

    public void getClassInfo(Class<?> clazz) {
        // 获取这个类中所有的属性信息
        Field[] declaredFields = clazz.getDeclaredFields();
        // 获取这个类中所有的方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
    }


    /**
     * 从类中获取指定的方法(这种处理不了重载的情况)
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethodFromClass(Class<?> clazz, String methodName) {
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        for (Method method : declaredMethods) {
//            if(method.getName().equals(methodName)){
//                return method;
//            }
//        }
        return getMethodFromClass(clazz, methodName, null);
    }

    public static Method getMethodFromClass(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        if (null == parameterTypes) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
            return null;
        }
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String, List<String>> getGenericTypeFields(Type type) {
        Map<String, List<String>> exitsMap = new LinkedHashMap<>();
        if (type instanceof ParameterizedType) {
            //如果是泛型,则调用泛型的处理方法
            doGetGenericTypeFields(type, exitsMap);
        } else {
            //如果不是泛型,则直接调用实体类的获取属性值的方式
            doGetModelFields((Class<?>) type, exitsMap);
        }
        return exitsMap;
    }

    /**
     * 处理泛型的方法
     */
    public static void doGetGenericTypeFields(Type type, Map<String, List<String>> map) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        map.putAll(getModelFields((Class<?>) parameterizedType.getRawType()));
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        for (Type argument : typeArguments) {
            if (argument instanceof Class) {
                map.putAll(getModelFields((Class<?>) argument));
            } else {

                doGetGenericTypeFields(argument, map);
            }
        }

    }

    public static Map<String, List<String>> getModelFields(Class<?> clazz) {
        // 选用linkedHashMap是为了有序,存复杂类型映射的结果
        // key-value: com.xxx.xxx.xx <-> ["int-bName","Integer-testInter"]
        Map<String, List<String>> exitsMap = new LinkedHashMap<>();
        doGetModelFields(clazz, exitsMap);
        return exitsMap;
    }

    public static List<String> getSimpleModelFields(Class<?> clazz){
        Map<String, List<String>> modelFields = getModelFields(clazz);
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> listEntry : modelFields.entrySet()) {
            List<String> value = listEntry.getValue();
            if(value!=null){
                list = value;
                break;
            }
        }
        List<String> stringList = list.stream().map(x -> x.split("-")[1]).collect(Collectors.toList());
        return stringList;
    }

    public static void doGetModelFields(Class<?> clazz, Map<String, List<String>> map) {
        // red.lixiang.tools.xxxx
        String clazzName = clazz.getCanonicalName();
        // xxxx
        String clazzSimpleName = clazz.getSimpleName();
        // 为了防止循环引用,先加入到map中
        List<String> list = new ArrayList<>();
        map.put(clazzName + "&" + clazzSimpleName, list);
        if (simpleClass(clazz)) {
            return;
        }
        // 这个方法只能获取到自己类中的Field,不包含父类的
        // 要注意有个this
        Field[] fields = getAllFields(clazz);

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.contains("this") || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Class<?> fieldType = field.getType();
            String fullName = fieldType.getCanonicalName();
            String simpleName = fieldType.getSimpleName();
            list.add(simpleName + "-" + fieldName);
            if (!simpleClass(fieldType)) {
                // 判断这个是不是基础属性,如果是的话,则可以结束了
                // 这里获取到的name是 com.platform.xxx.xxx.Model 这样的全路径
                // 如果是复杂属性的话,则先看map中有没有这个属性
                if (!map.containsKey(fullName + "&" + simpleName)) {
                    doGetModelFields(fieldType, map);
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> modelFields = getModelFields(A.class);
        System.out.println(modelFields);
    }

    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> curClazz = clazz;
        while (curClazz != null) {
            fieldList.addAll(Arrays.asList(curClazz.getDeclaredFields()));
            //获取当前类的父类
            curClazz = curClazz.getSuperclass();
        }
        return fieldList.toArray(new Field[0]);
    }


    class A {
        private String aName;
        private String aCCC;
        private B b;
    }

    class B {
        private A a;
        private C c;
        private String bName;
    }

    class C {
        private String cName;
    }


    /**
     * 判断这个类是否是基础类型
     *
     * @param parameterType
     * @return
     */
    public static boolean simpleClass(Class<?> parameterType) {
        return parameterType == String.class ||
                parameterType == Integer.class || parameterType == int.class ||
                parameterType == Double.class || parameterType == double.class ||
                parameterType == Long.class || parameterType == long.class ||
                parameterType == Float.class || parameterType == float.class ||
                parameterType == Boolean.class || parameterType == boolean.class ||
                parameterType == Date.class || Collection.class.isAssignableFrom(parameterType) ||
                Map.class.isAssignableFrom(parameterType) || parameterType == Object.class ||
                parameterType == Byte.class || parameterType == byte.class;
    }
}
