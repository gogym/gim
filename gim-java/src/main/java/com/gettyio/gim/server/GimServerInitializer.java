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
import com.gettyio.core.handler.codec.protobuf.ProtobufDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufEncoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import com.gettyio.core.handler.codec.websocket.WebSocketDecoder;
import com.gettyio.core.handler.codec.websocket.WebSocketEncoder;
import com.gettyio.core.handler.ssl.SslConfig;
import com.gettyio.core.handler.ssl.SslHandler;
import com.gettyio.core.handler.ssl.SslService;
import com.gettyio.core.handler.timeout.HeartBeatTimeOutHandler;
import com.gettyio.core.handler.timeout.IdleStateHandler;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.DefaultChannelPipeline;
import com.gettyio.gim.packet.MessageClass;


/**
 * GimServerInitializer.java
 *
 * @description:gim配置
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimServerInitializer extends ChannelInitializer {

    GimContext gimContext;
    Integer socketType;

    public GimServerInitializer(GimContext gimContext, Integer socketType) {
        this.gimContext = gimContext;
        this.socketType = socketType;
    }

    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        //获取责任链对象
        DefaultChannelPipeline pipeline = channel.getDefaultChannelPipeline();

        if (gimContext.gimConfig.isEnableSsl()) {
            //ssl配置
            SslConfig sSLConfig = new SslConfig();
            sSLConfig.setKeyFile(gimContext.gimConfig.getPkPath());
            sSLConfig.setKeyPassword(gimContext.gimConfig.getKeyPassword());
            sSLConfig.setKeystorePassword(gimContext.gimConfig.getKeystorePassword());
            sSLConfig.setTrustFile(gimContext.gimConfig.getTrustPath());
            sSLConfig.setTrustPassword(gimContext.gimConfig.getTrustPassword());
            //设置服务器模式
            sSLConfig.setClientMode(false);
            //设置单向验证或双向验证
            sSLConfig.setClientAuth(gimContext.gimConfig.isClientAuth());
            //初始化ssl服务
            SslService sSLService = new SslService(sSLConfig);
            pipeline.addFirst(new SslHandler(channel, sSLService));
        }

        if (socketType == SocketType.SOCKET) {
            // ----配置Protobuf处理器----
            pipeline.addLast(new ProtobufVarint32FrameDecoder());
            pipeline.addLast(new ProtobufDecoder(MessageClass.Message.getDefaultInstance()));
            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
            pipeline.addLast(new ProtobufEncoder());
            // ----Protobuf处理器END----
        } else if (socketType == SocketType.WEB_SOCKET) {
            //websocket编解码器
            pipeline.addLast(new WebSocketEncoder());
            pipeline.addLast(new WebSocketDecoder());
        }

        if (gimContext.gimConfig.isEnableHeartBeat()) {
            // 心跳起搏器
            pipeline.addLast(new IdleStateHandler(channel, gimContext.gimConfig.getHeartBeatInterval(), 0));
            // 心跳检测
            pipeline.addLast(new HeartBeatTimeOutHandler());
        }

        if (socketType == SocketType.SOCKET) {
            pipeline.addLast(new ChatServerHandler(gimContext));
        } else if (socketType == SocketType.WEB_SOCKET) {
            pipeline.addLast(new ChatWsServerHandler(gimContext));
        }
    }
}
