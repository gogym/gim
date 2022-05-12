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

import com.gettyio.core.channel.group.ChannelGroup;
import com.gettyio.core.channel.group.DefaultChannelGroup;
import com.gettyio.gim.cluster.config.ClusterConfig;
import com.gettyio.gim.cluster.handler.AbsChatHandler;
import com.gettyio.gim.cluster.handler.ChatListener;
import com.gettyio.gim.cluster.handler.ChatListenerHandler;
import com.gettyio.gim.cluster.handler.bshandler.AckHandler;
import com.gettyio.gim.cluster.handler.bshandler.HeartBeatHandler;
import com.gettyio.gim.cluster.handler.bshandler.SingleMsgHandler;
import com.gettyio.gim.cluster.listener.ChannelReadListener;
import com.gettyio.gim.cluster.listener.ChannelStatusListener;
import com.gettyio.gim.cluster.message.MessageEmitter;
import com.gettyio.gim.cluster.router.ClusterRoute;
import com.gettyio.gim.comm.ReqType;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GimContext.java
 *
 * @description:上下文
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimContext {

    /**
     * 配置类
     */
    private ClusterConfig clusterConfig;
    /**
     * 消息发送类
     */
    private MessageEmitter messageEmitter;
    /**
     * 集群类
     */
    private ClusterRoute clusterRoute;

    private ChannelStatusListener channelStatusListener;
    private ChannelReadListener channelReadListener;


    /**
     * 实例一个连接容器用户保存TCP连接
     */
    private ChannelGroup channels = new DefaultChannelGroup();
    /**
     * userId与ChannelId的映射关系，这里注意要使用线程安全的ConcurrentMap
     */
    private ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap<>();
    /**
     * 群组成员映射，仅当非集群模式时保存群组映射
     */
    private ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = new ConcurrentHashMap<>();

    /**
     * 业务处理器集合
     */
    private Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    /**
     * 消息监听
     */
    private ChatListener chatListener = new ChatListenerHandler(handlerMap);

    public GimContext(ClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
        this.messageEmitter = new MessageEmitter(this);
        this.clusterRoute = new ClusterRoute(clusterConfig);

        //添加消息业务处理器
        handlerMap.put(ReqType.SINGLE_MSG_REQ, new SingleMsgHandler(this));
        handlerMap.put(ReqType.ACK_REQ, new AckHandler(this));
        handlerMap.put(ReqType.HEART_BEAT_REQ, new HeartBeatHandler(this));
    }

    //-----------------------------------get----------------------------------------------


    public ClusterConfig getClusterConfig() {
        return clusterConfig;
    }

    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    public ChannelGroup getChannels() {
        return channels;
    }

    public ConcurrentMap<String, String> getUserChannelMap() {
        return userChannelMap;
    }

    public ConcurrentMap<String, CopyOnWriteArrayList<String>> getGroupUserMap() {
        return groupUserMap;
    }

    public Map<Integer, AbsChatHandler<?>> getHandlerMap() {
        return handlerMap;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }

    public ClusterRoute getClusterRoute() {
        return clusterRoute;
    }

    public ChannelStatusListener getChannelStatusListener() {
        return channelStatusListener;
    }

    public ChannelReadListener getChannelReadListener() {
        return channelReadListener;
    }


    //-----------------------------------set----------------------------------------------



    public void setChannelStatusListener(ChannelStatusListener channelStatusListener) {
        this.channelStatusListener = channelStatusListener;
    }


    public void setChannelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
    }

}
