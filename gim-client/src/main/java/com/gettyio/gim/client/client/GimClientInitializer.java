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
package com.gettyio.gim.client.client;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.channel.starter.ConnectHandler;
import com.gettyio.core.handler.codec.protobuf.ProtobufDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufEncoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import com.gettyio.core.handler.ssl.SslConfig;
import com.gettyio.core.handler.ssl.SslHandler;
import com.gettyio.core.handler.ssl.SslService;
import com.gettyio.core.handler.timeout.ReConnectHandler;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.DefaultChannelPipeline;
import com.gettyio.gim.packet.MessageClass;


/**
 * GimClientInitializer.java
 *
 * @description:处理器配置
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimClientInitializer extends ChannelInitializer {

    InternalLogger logger = InternalLoggerFactory.getInstance(GimClientInitializer.class);
    GimContext gimContext;
    ConnectHandler connectHandler;

    public GimClientInitializer(GimContext gimContext, ConnectHandler connectHandler) {
        this.gimContext = gimContext;
        this.connectHandler = connectHandler;
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
            //设置客户端模式
            sSLConfig.setClientMode(true);
            //设置单向验证或双向验证
            sSLConfig.setClientAuth(gimContext.gimConfig.isClientAuth());
            //初始化ssl服务
            SslService sSLService = new SslService(sSLConfig);
            pipeline.addFirst(new SslHandler(channel, sSLService));
        }


        // ----配置Protobuf处理器----
        // 用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 配置Protobuf解码处理器，消息接收到了就会自动解码，ProtobufDecoder是netty自带的，Message是自己定义的Protobuf类
        pipeline.addLast(new ProtobufDecoder(MessageClass.Message.getDefaultInstance()));
        // 用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // 配置Protobuf编码器，发送的消息会先经过编码
        pipeline.addLast(new ProtobufEncoder());
        // ----Protobuf处理器END----

        if (gimContext.gimConfig.isEnableReConnect()) {
            //是否开启断线重连
            pipeline.addLast(new ReConnectHandler(connectHandler));
        }
        pipeline.addLast(new ChatClientHandler(gimContext));
    }
}
