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
import com.gettyio.core.channel.config.ClientConfig;
import com.gettyio.core.channel.starter.ConnectHandler;
import com.gettyio.core.channel.starter.NioClientStarter;
import com.gettyio.gim.client.expansion.DelayMsgQueueListener;
import com.gettyio.gim.client.expansion.HeartBeatHandler;
import com.gettyio.gim.client.listener.ChannelBindListener;
import com.gettyio.gim.client.listener.ChannelReadListener;
import com.gettyio.gim.client.listener.ChannelStatusListener;

/**
 * GimClient.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimClient {

    /**
     * 总的配置类
     */
    private GimConfig gimConfig;
    private GimContext gimContext;
    private NioClientStarter nioClientStarter;
    private ConnectHandler connectHandler;

    public GimClient(GimConfig gimConfig, ChannelStatusListener gimListener) {
        this.gimConfig = gimConfig;
        gimContext = new GimContext(gimConfig, gimListener);
    }


    public GimClient channelReadListener(ChannelReadListener channelReadListener) {
        gimContext.channelReadListener(channelReadListener);
        return this;
    }

    public GimClient channelBindListener(ChannelBindListener channelBindListener) {
        gimContext.channelBindListener(channelBindListener);
        return this;
    }

    /**
     * Description: start
     *
     * @see
     */
    public void start() throws Exception {
        // 检查配置
        checkConfig();
        // 启动
        start0();
    }


    /**
     * 关闭
     */
    public void shutDown() {

        if (nioClientStarter != null) {
            nioClientStarter.shutdown();
        }
    }

    private void checkConfig() throws Exception {

        // 启动前，做系统自查，检查集群，离线等配置等
        if (gimConfig == null) {
            throw new NullPointerException("[GimConfig can't null]");
        }

        // 检查端口号
        if (gimConfig.getHost() == null) {
            throw new NullPointerException("[the host can't null]");
        }

        // 检查端口号
        if (gimConfig.getPort() == null) {
            throw new NullPointerException("[the port can't null]");
        }

        if (gimContext.channelReadListener == null) {
            throw new NullPointerException("[the channelReadListener can't null]");
        }

        if (null == gimContext.channelStatusListener) {
            throw new NullPointerException("[the channelStatusListener can't null]");
        }

    }

    /**
     * Description: 处理延迟队列
     *
     * @see
     */
    private void startDelayQueueRunable() {
        new Thread(new DelayMsgQueueListener(gimContext)).start();
    }


    /**
     * 内部启动
     *
     * @return void
     * @params []
     */
    private void start0() throws Exception {

        connectHandler = new ConnectHandlerImp();
        //初始化配置对象
        ClientConfig aioClientConfig = new ClientConfig();
        aioClientConfig.setHost(gimConfig.getHost());
        aioClientConfig.setPort(gimConfig.getPort());
        nioClientStarter = new NioClientStarter(aioClientConfig).channelInitializer(new GimClientInitializer(gimContext, connectHandler));
        //启动服务
        nioClientStarter.start(connectHandler);

        //如果开启了重发
        if (gimConfig.isAutoRewrite()) {
            startDelayQueueRunable();
        }
    }

    class ConnectHandlerImp implements ConnectHandler {
        @Override
        public void onCompleted(SocketChannel channel) {
            gimContext.channelStatusListener.channelAdd(gimContext, channel.getChannelId());
            if (gimContext.gimConfig.isEnableHeartBeat()) {
                //是否开启了心跳
                HeartBeatHandler heartBeatHandler = new HeartBeatHandler(gimContext);
                heartBeatHandler.start();
            }
        }

        @Override
        public void onFailed(Throwable exc) {
            throw new RuntimeException(exc);
        }
    }


}
