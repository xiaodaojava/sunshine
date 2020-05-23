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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import red.lixiang.tools.jdk.ByteTools;
import red.lixiang.tools.jdk.ToolsLogger;
import red.lixiang.tools.jdk.file.FilePart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {


    private String workDir;

    /**
     * 总共需要传输的数量
     */
    private long count;

    /**
     * 已完成的传输
     */
    private long finished=0;


    /**
     * Creates a client-side handler.
     * @param workDir
     */
    public ObjectEchoClientHandler(String workDir) {
        this.workDir = workDir;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message if this handler is a client-side handler.
        // 客户端连接上之后,就要向服务器发送文件
        try {
             count = Files.list(Paths.get(workDir)).filter(x -> x.getFileName().toString().contains("hbb")).map(x -> {
                try {
                    byte[] bytes = Files.readAllBytes(x);
                    FilePart filePart = ByteTools.byteToObject(bytes, FilePart.class);
                    ToolsLogger.plainInfo("{} 正在传送中,共{},当前{}",filePart.getFileName(),filePart.getTotalPart(),filePart.getCurrentPart());
                    ctx.writeAndFlush(filePart);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).count();
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发完之后,就可以直接关掉自己了
       // ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the server.
        if("OK".equals(msg.toString())){
            finished++;
            if(count==finished){
                //全部传输完, 就可以关闭连接了
                ctx.channel().close();
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
