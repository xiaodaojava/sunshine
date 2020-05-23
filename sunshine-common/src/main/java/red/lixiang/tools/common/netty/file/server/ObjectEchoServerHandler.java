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
package red.lixiang.tools.common.netty.file.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import red.lixiang.tools.jdk.ByteTools;
import red.lixiang.tools.jdk.file.FilePart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

    private final String workDir;

    private  int finished = 0;

    public ObjectEchoServerHandler(String workDir) {
        this.workDir = workDir;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("in read");
        // Echo back the received object to the client.
        //ctx.write(msg);
        FilePart filePart = (FilePart)msg;
        String fileName = null;
        if(filePart.getCurrentPart()==0){
            //是描述文件
             fileName = workDir+"desc.hbb";
        }else {
             fileName = workDir+filePart.getCurrentPart()+".hbb";
        }
        try {
            Files.write(Paths.get( fileName),ByteTools.objectToByte(filePart));
            ctx.writeAndFlush("OK");
            if((++finished)==filePart.getTotalPart()+1){
                //全部都完成了,可以关服务了
                ctx.channel().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
