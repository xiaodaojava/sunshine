/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package red.lixiang.tools.common.netty.file.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import red.lixiang.tools.jdk.file.FileTools;

import javax.net.ssl.SSLException;

/**
 *
 */
public final class ObjectEchoClient {

    final boolean SSL = System.getProperty("ssl") != null;
    final String HOST = System.getProperty("host", "127.0.0.1");
    final int PORT = Integer.parseInt(System.getProperty("port", "8007"));


    Channel clientChannel = null;

    public void closeClient() {
        if (clientChannel != null) {
            clientChannel.close();
        }
    }

    public void startClient(String workDir) {
        // Configure SSL.
        final SslContext sslCtx;
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            if (SSL) {
                sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } else {
                sslCtx = null;
            }
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoClientHandler(workDir));
                        }
                    });

            // Start the connection attempt.
            clientChannel = b.connect(HOST, PORT).sync().channel();
            clientChannel.closeFuture().sync();
        } catch (SSLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        String file = "/Users/lixiang/Desktop/testPDF.pdf";
        String workDir = FileTools.splitFile(file);
        ObjectEchoClient client = new ObjectEchoClient();
        client.startClient(workDir);
    }
}
