package red.lixiang.tools.jdk;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author lixiang
 * @date 2020/2/6
 **/
public class ArrayTools {

    public static byte[] concatBytes(byte[] a, byte[] b) {
        byte[] c= new byte[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 合并多个数组
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static <T> T[] removeIf(T[] origin, Predicate<? super T> filter){
        T[] newArray = Arrays.copyOf(origin,origin.length);
        int nIndex = 0;
        for (T t : origin) {
            if(!filter.test(t)) {
                newArray[nIndex++]=t;
            }
        }
        return Arrays.copyOf(newArray,nIndex);
    }

    /**
     * 从byte数组中移除符合条件的数据.并且会更改size
     * <pre>{@code
     *     byte[] bytes = new byte[]{1,2,3,4};
     *     bytes =removeIf(bytes, x-> x==0x1);
     * }</pre>
     *
     * @param origin
     * @param filter
     * @return
     */
    public static byte[] removeIf(byte[] origin, Predicate<Integer> filter){
        byte[] newArray = Arrays.copyOf(origin,origin.length);
        int nIndex = 0;
        for (byte t : origin) {
            if(!filter.test((int)t)) {
                newArray[nIndex++]=t;
            }
        }
        return Arrays.copyOf(newArray,nIndex);
    }

    public static void main(String[] args) {
        byte[] bytes = new byte[]{1,2,3,4};
        bytes =removeIf(bytes, x-> x==0x1);
        System.out.println(bytes);
    }

}
