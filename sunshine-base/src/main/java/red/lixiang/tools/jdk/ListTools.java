package red.lixiang.tools.jdk;


import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by lixiang on 14/07/2017.
 */
public class ListTools {


    /**
     * List根据某一个属性去重
     * List<ProductAttribute> colorList
     * =allAttr.stream()
     * .filter(ListUtils.distinctByKey(ProductAttribute::getColorCode))
     * .collect(Collectors.toList());
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 从list中随机返回一个值（效率未验）
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getRandomValueFromList(List<T> list) {

        return list.get(new Random().nextInt(list.size()));

    }

    /**
     * 从list中随机返回n个值（效率未验）
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> getRandomValueFromList(List<T> list, Integer n) {
        List<T> newList = new ArrayList<>();
        n = n > list.size() ? list.size() : n;
        for (int i = 0; i < n; i++) {
            int index = new Random().nextInt(n - i);
            newList.add(list.remove(index));
        }
        return newList;
    }

    /**
     * 通过list中实体中的一个值来判断是否存在,存在则返回所有的
     *
     * @param list
     * @param field
     * @param <T>
     * @return
     */
    public static <T> List<T> containsByField(List<T> list, String field, Object value, Class<T> clazz) {
        List<T> res = new ArrayList<>();

        try {
            Field dbField = clazz.getDeclaredField(field);
            dbField.setAccessible(true);
            for (T t : list) {
                Object o = dbField.get(t);
                if (value.equals(o)) {
                    res.add(t);
                }
            }
        } catch (NoSuchFieldException e) {
            return res;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 向list中添加一条数据，并返回添加的Index值（只限于ArrayList）
     *
     * @param list
     * @param t
     * @param <T>
     * @return
     */
    public static <T> int setReturnIndex(ArrayList<T> list, T t) {
        list.add(t);
        return list.indexOf(t);
    }


    public static <T> List<T> getLastList(List<T> t, int size) {
        //获取后面几个元素
        size = Math.min(size, t.size());
        List<T> newList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            T msg = t.get(t.size() - (size - i));
            newList.add(msg);
        }
        return newList;
    }

    /**
     * 从list中获取一个,如果有多个的话,只获取第一个
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T getOne(List<T> t) {
        if (null == t || t.isEmpty()) {
            return null;
        }
        return t.get(0);
    }

    /**
     * 从list中获取一个,如果有多个的话,则返回null
     *
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> T getOneStrict(List<T> tList) {
        if (null != tList && tList.isEmpty()) {
            return null;
        }
        if (null != tList && tList.size() == 1) {
            return tList.get(0);
        }
        return null;
    }


    /**
     * 将一个list均分成n个list
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public static boolean isBlank(List list) {
        return null == list || list.isEmpty();
    }

    public static boolean isNotBlank(List list) {
        return !isBlank(list);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("ccc");
        System.out.println(list);

    }

    static class TestModel {
        private String name;
        private int age;

        public TestModel(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
