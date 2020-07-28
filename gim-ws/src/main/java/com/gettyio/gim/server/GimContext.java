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

import com.gettyio.core.channel.group.ChannelGroup;
import com.gettyio.core.channel.group.DefaultChannelGroup;
import com.gettyio.gim.bind.GimBind;
import com.gettyio.gim.cluster.ClusterRoute;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.handler.BaseChatHandler;
import com.gettyio.gim.handler.ChatListener;
import com.gettyio.gim.handler.bshandler.*;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.listener.*;
import com.gettyio.gim.message.MessagEmitter;
import com.gettyio.gim.message.MessageDelayPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.DelayQueue;

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
    public GimConfig gimConfig;
    /**
     * 消息发送类
     */
    public MessagEmitter messagEmitter;
    /**
     * 绑定类
     */
    public GimBind gimBind;
    /**
     * 集群类
     */
    public ClusterRoute clusterRoute;


    /**
     * 业务监听
     */
    public ChannelBindListener channelBindListener;
    public ChannelStatusListener channelStatusListener;
    public ChannelAckListener channelAckListener;
    public ChannelReadListener channelReadListener;
    public OfflineMsgListener offlineMsgListener;


    /**
     * 实例一个连接容器用户保存TCP连接
     */
    public ChannelGroup channels = new DefaultChannelGroup();
    /**
     * userId与ChannelId的映射关系，这里注意要使用线程安全的ConcurrentMap
     */
    public ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap<>();
    /**
     * 群组成员映射
     */
    public ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = new ConcurrentHashMap<>();
    /**
     * 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
     */
    public DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<>();
    /**
     * 业务处理器集合
     */
    public Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    /**
     * 消息监听
     */
    public ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig) {
        this.gimConfig = gimConfig;
        this.messagEmitter = new MessagEmitter(this);
        this.gimBind = new GimBind(this);
        this.clusterRoute = new ClusterRoute(this);

        //添加消息处理器
        handlerMap.put(Type.BIND_REQ, new BindHandler(this));
        handlerMap.put(Type.UNBIND_REQ, new BindHandler(this));
        handlerMap.put(Type.SINGLE_MSG_REQ, new SingleMsgHandler(this));
        handlerMap.put(Type.GROUP_MSG_REQ, new GroupMsgHandler(this));
        handlerMap.put(Type.ACK_REQ, new AckHandler(this));
        handlerMap.put(Type.HEART_BEAT_REQ, new HeartBeatHandler(this));
    }


    public GimContext channelStatusListener(ChannelStatusListener channelStatusListener) {
        this.channelStatusListener = channelStatusListener;
        return this;
    }

    public GimContext channelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
        return this;
    }

    public GimContext channelAckListener(ChannelAckListener channelAckListener) {
        this.channelAckListener = channelAckListener;
        return this;
    }

    public GimContext channelBindListener(ChannelBindListener channelBindListener) {
        this.channelBindListener = channelBindListener;
        return this;
    }

    public GimContext offlineMsgListener(OfflineMsgListener offlineMsgListener) {
        this.offlineMsgListener = offlineMsgListener;
        return this;
    }

    public ClusterRoute getClusterRoute() {
        return clusterRoute;
    }
}
