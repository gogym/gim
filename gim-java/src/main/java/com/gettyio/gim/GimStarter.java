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
package com.gettyio.gim;


import com.gettyio.core.channel.config.ServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;
import com.gettyio.gim.cluster.ClusterMsgListener;
import com.gettyio.gim.queue.DelayMsgQueueListener;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.server.GimServerInitializer;

/**
 * GimStarter.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimStarter {

    /**
     * 总的配置类
     */
    private GimConfig gimConfig;
    /**
     * 上下文
     */
    private GimContext gimContext;

    private AioServerStarter server;

    /**
     * 启动监听
     */
    private OnStartListener onStartListener;


    public GimStarter(GimConfig gimConfig) {
        this.gimConfig = gimConfig;
        gimContext = new GimContext(gimConfig);
    }

    /**
     * start
     *
     * @throws Exception
     * @see
     */
    public void start(OnStartListener onStartListener) throws Exception {
        this.onStartListener = onStartListener;
        // 检查配置
        checkConfig();
        start0();
    }


    /**
     * 停止
     */
    public void shutDown() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void checkConfig() throws Exception {

        // 启动前，做系统自查，检查集群，离线等配置等
        if (gimConfig == null) {
            throw new Exception("[GimConfig can't be not null]");
        }

        // 检查端口号
        if (gimConfig.getPort() == null) {
            throw new Exception("[the port can't be not null]");
        }

    }

    /**
     * Description: 处理延迟队列监听
     *
     * @see
     */
    private void startDelayQueueRunable() {
        new Thread(new DelayMsgQueueListener(gimContext)).start();
    }

    private void startClusterRunable() {
        new Thread(new ClusterMsgListener(gimContext)).start();
    }


    private void start0() {
        //初始化配置对象
        ServerConfig aioServerConfig = new ServerConfig();
        //设置host,不设置默认localhost
        aioServerConfig.setHost(gimConfig.getHost());
        //设置端口号
        aioServerConfig.setPort(gimConfig.getPort());
        aioServerConfig.setServerChunkSize(gimConfig.getServerChunkSize());
        server = new AioServerStarter(aioServerConfig);
        server.channelInitializer(new GimServerInitializer(gimContext));
        try {
            server.start();
            //启动后回调
            onStartListener.onStart(gimContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果开启了重发
        if (gimConfig.isAutoRewrite()) {
            startDelayQueueRunable();
        }

        //是否开启了集群
        if (gimConfig.isEnableCluster()) {
            startClusterRunable();
        }

    }


    public interface OnStartListener {
        void onStart(GimContext gimContext);
    }

}
