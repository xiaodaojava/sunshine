package red.lixiang.tools.jdk;

import java.util.Arrays;

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
}
