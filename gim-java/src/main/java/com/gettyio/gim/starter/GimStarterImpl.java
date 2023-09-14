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
package com.gettyio.gim.starter;


import com.gettyio.core.channel.config.ServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.util.ThreadPool;
import com.gettyio.gim.cluster.ClusterMsgListener;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.server.GimHost;
import com.gettyio.gim.server.GimServerInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * GimStarter.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimStarterImpl implements GimStarter {

    InternalLogger logger = InternalLoggerFactory.getInstance(GimStarterImpl.class);

    private GimContext gimContext;

    /**
     * 配置类
     */
    private final GimConfig gimConfig;

    /**
     * 启动监听
     */
    private final OnStartListener onStartListener;

    /**
     * 服务列表
     */
    private final List<AioServerStarter> servers = new ArrayList<>();


    /**
     * 构造方法
     *
     * @param gimConfig
     */
    public GimStarterImpl(GimConfig gimConfig, OnStartListener onStartListener) {
        this.gimConfig = gimConfig;
        this.onStartListener = onStartListener;
    }


    @Override
    public void start() {
        try {
            // 检查配置
            checkConfig();
            start0();
        } catch (Exception e) {
            logger.error("服务启动异常，请检查相关配置！", e);
            System.exit(1);
        }
    }


    @Override
    public void shutDown() {
        //停止服务
        for (AioServerStarter serverStarter : servers) {
            serverStarter.shutdown();
        }
        if (gimContext != null) {
            gimContext.setRun(false);
        }
    }

    /**
     * 启动前，做系统自查，检查集群，离线等配置等是否正确
     *
     * @throws Exception
     */
    private void checkConfig() throws Exception {

        if (gimConfig == null) {
            throw new NullPointerException("[GimConfig is null]");
        }

        // 检查端口号
        if (gimConfig.getHosts() == null || gimConfig.getHosts().size() == 0) {
            throw new NullPointerException("[the host is null]");
        }

    }


    /**
     * 内部启动
     */
    private void start0() throws Exception {
        //初始化上下文参数
        gimContext = new GimContext(gimConfig);

        //循环启动服务
        for (GimHost gimHost : gimConfig.getHosts()) {
            //初始化配置对象
            ServerConfig aioServerConfig = new ServerConfig();
            //设置host,不设置默认localhost
            aioServerConfig.setHost(gimHost.getHost());
            //设置端口号
            aioServerConfig.setPort(gimHost.getPort());
            final AioServerStarter server = new AioServerStarter(aioServerConfig);
            server.channelInitializer(new GimServerInitializer(gimContext, gimHost.getSocketType()));
            //启动服务
            server.start();
            //添加到列表
            servers.add(server);
        }

        gimContext.setRun(true);
        //是否开启了集群
        if (gimConfig.isEnableCluster()) {
            new Thread(new ClusterMsgListener(gimContext)).start();
        }

        if (onStartListener != null) {
            //启动成功后回调
            onStartListener.onStart(gimContext);
        }
    }


}
