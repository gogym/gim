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
package com.gettyio.gim.cluster.server;


import com.gettyio.core.channel.config.ServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.util.ThreadPool;
import com.gettyio.gim.cluster.config.ClusterConfig;


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

    /**
     * 线程池
     */
    ThreadPool threadPool = new ThreadPool(ThreadPool.FixedThread, 5);

    /**
     * 配置类
     */
    private ClusterConfig clusterConfig;

    /**
     * 启动监听
     */
    private OnStartListener onStartListener;

    /**
     * 服务列表
     */
    private List<AioServerStarter> servers = new ArrayList<>();


    /**
     * 构造方法
     *
     * @param clusterConfig
     */
    public GimStarterImpl(ClusterConfig clusterConfig,OnStartListener onStartListener) {
        this.clusterConfig = clusterConfig;
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
        if (!threadPool.isShutDown()) {
            threadPool.shutdownNow();
        }

        //停止服务
        for (AioServerStarter serverStarter : servers) {
            serverStarter.shutdown();
        }
    }

    /**
     * 启动前，做系统自查，检查集群，离线等配置等是否正确
     *
     * @throws Exception
     */
    private void checkConfig() throws Exception {

        if (clusterConfig == null) {
            throw new NullPointerException("[GimConfig is null]");
        }

        // 检查端口号
        if (clusterConfig.getHosts() == null || clusterConfig.getHosts().size() == 0) {
            throw new NullPointerException("[the host is null]");
        }

    }


    /**
     * 内部启动
     */
    private void start0() throws Exception {
        //初始化上下文参数
        GimContext gimContext = new GimContext(clusterConfig);

        //循环启动服务
        for (GimHost gimHost : clusterConfig.getHosts()) {
            //初始化配置对象
            ServerConfig aioServerConfig = new ServerConfig();
            //设置host,不设置默认localhost
            aioServerConfig.setHost(gimHost.getHost());
            //设置端口号
            aioServerConfig.setPort(gimHost.getPort());
            final AioServerStarter server = new AioServerStarter(aioServerConfig);
            //server.channelInitializer(new GimServerInitializer(gimContext, gimHost.getSocketType()));
            //启动服务
            server.start();
            //添加到列表
            servers.add(server);
        }



        if(onStartListener!=null) {
            //启动成功后回调
            onStartListener.onStart(gimContext);
        }
    }


}
