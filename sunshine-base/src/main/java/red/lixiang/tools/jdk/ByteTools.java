package red.lixiang.tools.jdk;

import red.lixiang.tools.base.conf.KubsConfig;

import java.io.*;

/**
 * @author lixiang
 * @date 2020/5/23
 **/
public class ByteTools {

    /**
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public  static <T> T byteToObject(byte[] bytes,Class<T> clazz){
        Object o = byteToObject(bytes);
        return (T)o;
    }

    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (oi != null) {
                    oi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (oo != null) {
                    oo.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

}
