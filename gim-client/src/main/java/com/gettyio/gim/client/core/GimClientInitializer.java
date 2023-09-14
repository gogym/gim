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
package com.gettyio.gim.client.core;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.channel.starter.ConnectHandler;
import com.gettyio.core.handler.ssl.SSLConfig;
import com.gettyio.core.handler.ssl.SSLHandler;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.ChannelPipeline;
import com.gettyio.expansion.handler.codec.protobuf.ProtobufDecoder;
import com.gettyio.expansion.handler.codec.protobuf.ProtobufEncoder;
import com.gettyio.expansion.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import com.gettyio.expansion.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import com.gettyio.expansion.handler.timeout.ReConnectHandler;
import com.gettyio.gim.packet.MessageClass;


/**
 * GimClientInitializer.java
 *
 * @description:处理器配置
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimClientInitializer implements ChannelInitializer {

    GimContext gimContext;
    OnConnectLintener onConnectLintener;

    public GimClientInitializer(GimContext gimContext, OnConnectLintener onConnectLintener) {
        this.gimContext = gimContext;
        this.onConnectLintener = onConnectLintener;
    }


    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        //获取责任链对象
        ChannelPipeline pipeline = channel.getDefaultChannelPipeline();

        if (gimContext.getGimConfig().isEnableSsl()) {
            //ssl配置
            SSLConfig sSLConfig = new SSLConfig();
            sSLConfig.setKeyFile(gimContext.getGimConfig().getPkPath());
            sSLConfig.setKeyPassword(gimContext.getGimConfig().getKeyPassword());
            sSLConfig.setKeystorePassword(gimContext.getGimConfig().getKeystorePassword());
            sSLConfig.setTrustFile(gimContext.getGimConfig().getTrustPath());
            sSLConfig.setTrustPassword(gimContext.getGimConfig().getTrustPassword());
            //设置客户端模式
            sSLConfig.setClientMode(true);
            //设置单向验证或双向验证
            sSLConfig.setClientAuth(gimContext.getGimConfig().isClientAuth());
            //初始化ssl服务
            pipeline.addFirst(new SSLHandler(sSLConfig));
        }

        // ----配置Protobuf处理器----
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(MessageClass.Message.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        // ----Protobuf处理器END----

        if (gimContext.getGimConfig().isEnableReConnect()) {
            //是否开启断线重连
            pipeline.addLast(new ReConnectHandler(new ConnectHandler() {
                @Override
                public void onCompleted(SocketChannel socketChannel) {
                    onConnectLintener.onCompleted(gimContext);
                }

                @Override
                public void onFailed(Throwable throwable) {
                    onConnectLintener.onFailed(throwable);
                }
            }));
        }
        pipeline.addLast(new ChatClientHandler(gimContext));
    }
}
