package red.lixiang.tools.jdk.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author lixiang
 * @date 2020/5/10
 **/
public class NioFileClient {

    public void receiveFile(){
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));
            File file = new File("E:\\" + 11 + ".txt");
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
            buffer.clear();
            int num = 0;
            while ((num=fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
            if (num == -1) {
                fileChannel.close();
                socketChannel.shutdownOutput();
            }
            // 接受服务器

            socketChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
            buffer.clear();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
