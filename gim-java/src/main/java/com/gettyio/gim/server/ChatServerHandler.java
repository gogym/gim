/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gettyio.gim.server;


import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.ChannelHandlerContext;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.gim.comm.SocketType;
import com.gettyio.gim.packet.MessageClass;


/**
 * ChatServerHandler.java
 *
 * @description:处理类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<MessageClass.Message> {

    InternalLogger logger = InternalLoggerFactory.getInstance(ChatServerHandler.class);
    GimContext gimContext;

    public ChatServerHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public void channelAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().getChannelId() + " connection successful.");
        //把socketType随通道传递下去，便于区分
        ctx.channel().setChannelAttribute("socketType", SocketType.SOCKET);
        gimContext.getChannels().add(ctx.channel());
        if (gimContext.getChannelStatusListener() != null) {
            gimContext.getChannelStatusListener().channelAdd(gimContext, ctx.channel().getChannelId());
        }
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().getChannelId() + " disconnected");
        gimContext.getGimBind().unbindByChannelId(ctx.channel().getChannelId());
        if (gimContext.getChannelStatusListener() != null) {
            gimContext.getChannelStatusListener().channelClose(ctx.channel().getChannelId());
        }

    }


    @Override
    public void channelRead0(SocketChannel socketChannel, MessageClass.Message message) throws Exception {
        // 消息会在这个方法接收到，msg就是经过解码器解码后得到的消息，框架自动帮你做好了粘包拆包和解码的工作
        gimContext.getChatListener().read(message, socketChannel);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        logger.error(ctx.channel().getChannelId() + " Exception, closed.", cause);
        ctx.channel().close();
        if (gimContext.getChannelStatusListener() != null) {
            gimContext.getChannelStatusListener().channelClose(ctx.channel().getChannelId());
        }
    }
}
