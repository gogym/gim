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
import com.gettyio.core.handler.codec.websocket.frame.BinaryWebSocketFrame;
import com.gettyio.core.handler.codec.websocket.frame.WebSocketFrame;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.gim.packet.MessageClass;


/**
 * ChatServerHandler.java
 *
 * @description:处理类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class ChatWsServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    InternalLogger logger = InternalLoggerFactory.getInstance(ChatServerHandler.class);
    GimContext gimContext;

    public ChatWsServerHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public void channelAdded(SocketChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + "ws connection successful.");

        aioChannel.setChannelAttribute("socketType", SocketType.WEB_SOCKET);
        gimContext.channels.add(aioChannel);
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelAdd(gimContext, aioChannel.getChannelId());
        }
    }

    @Override
    public void channelClosed(SocketChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + " disconnected");
        gimContext.gimBind.unbindByChannelId(aioChannel.getChannelId());
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelClose(aioChannel.getChannelId());
        }

    }


    @Override
    public void channelRead0(SocketChannel socketChannel, WebSocketFrame frame) throws Exception {

        if (frame instanceof BinaryWebSocketFrame) {
            byte[] data = frame.getPayloadData();
            MessageClass.Message message = MessageClass.Message.parseFrom(data);
            // 消息会在这个方法接收到，msg就是经过解码器解码后得到的消息，框架自动帮你做好了粘包拆包和解码的工作
            gimContext.chatListener.read(message, socketChannel);
        }
    }


    @Override
    public void exceptionCaught(SocketChannel socketChannel, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        logger.error(socketChannel.getChannelId() + " Exception, closed.", cause);
        socketChannel.close();
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelClose(socketChannel.getChannelId());
        }
    }
}
