package red.lixiang.tools.jdk.io;


import java.io.*;
import java.nio.CharBuffer;

/**
 * 和IO相关的一些工具类
 * @author lixiang
 * @date 2019/12/13
 **/
public class IOTools {

    /** 2K chars (4K bytes) */
    private static final int BUF_SIZE = 0x800;

    /**
     * 用完之后要关流
     *
     * @param from
     * @return
     * @throws IOException
     */
    public static long copy(Readable from, Appendable to) throws IOException {
        CharBuffer buf = CharBuffer.allocate(BUF_SIZE);
        long total = 0;
        while (from.read(buf) != -1) {
            buf.flip();
            to.append(buf);
            total += buf.remaining();
            buf.clear();
        }
        return total;
    }
    /**
     * 用完之后要关流
     *
     * @param from
     * @return
     * @throws IOException
     */
    public static String readString(InputStream from) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        while((len = from.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return new String(bos.toByteArray());
    }
    /**
     * 用完之后要关流
     *
     * @param from
     * @return
     * @throws IOException
     */
    public static byte[] readByte(InputStream from) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        while((len = from.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return (bos.toByteArray());
    }
    /**
     * 用完之后要关流
     *
     * @param from
     * @return
     * @throws IOException
     */
    public static String readString(InputStreamReader from) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        int len;
        while((len = from.read(buffer)) != -1) {
            builder.append(buffer,0,len);
        }
        return builder.toString();
    }
    public static String readStringFromReader(Reader from) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        int len;
        while((len = from.read(buffer)) != -1) {
            builder.append(buffer,0,len);
        }
        return builder.toString();
    }
}
