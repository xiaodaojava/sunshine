package red.lixiang.tools.jdk.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author lixiang
 * @date 2020/5/10
 **/
public class NioFileServer {

    /**
     * 1MB的缓存
     */
    private ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

    /**
     * 使用Map保存每个连接，当OP_READ就绪时，根据key找到对应的文件对其进行写入。
     * 若将其封装成一个类，作为值保存，可以再上传过程中显示进度等等
     */
    Map<SelectionKey, FileChannel> fileMap = new HashMap<>();

    public static void main(String[] args) {
        NioFileServer server = new NioFileServer();
        server.startServer();
    }

    public void startServer() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(520520));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器已开启...");
            while (true) {
                // 该方法会阻塞
                int num = selector.select();
                if (num == 0) {
                    continue;
                }
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel1 = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel1.accept();
                        if (socketChannel == null) {
                            continue;
                        }
                        socketChannel.configureBlocking(false);
                        SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);
                        InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        File file = new File(remoteAddress.getHostName() + "_" + remoteAddress.getPort() + ".txt");
                        FileChannel fileChannel = new FileOutputStream(file).getChannel();
                        fileMap.put(key1, fileChannel);
                        System.out.println(socketChannel.getRemoteAddress() + "连接成功...");
                        writeToClient(socketChannel);
                    } else if (key.isReadable()) {
                        readData(key);
                    }
                    // NIO的特点只会累加，已选择的键的集合不会删除，ready集合会被清空
                    // 只是临时删除已选择键集合，当该键代表的通道上再次有感兴趣的集合准备好之后，又会被select函数选中
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToClient(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        buffer.put((socketChannel.getRemoteAddress() + "连接成功").getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }

    private void readData(SelectionKey key) throws IOException {
        FileChannel fileChannel = fileMap.get(key);
        buffer.clear();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        try {
            while ((num = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                // 写入文件
                fileChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return;
        }
        // 调用close为-1 到达末尾
        if (num == -1) {
            fileChannel.close();
            System.out.println("上传完毕");
            buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
            buffer.clear();
            socketChannel.write(buffer);
            key.cancel();
        }
    }

}
