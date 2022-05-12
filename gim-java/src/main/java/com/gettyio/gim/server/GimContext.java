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
import com.gettyio.gim.comm.ReqType;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.handler.ChatListenerHandler;
import com.gettyio.gim.handler.ChatListener;
import com.gettyio.gim.handler.bshandler.*;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.listener.*;
import com.gettyio.gim.message.MessageEmitter;

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
     * 服务是否运行中
     */
    private boolean run=false;

    /**
     * 配置类
     */
    private final GimConfig gimConfig;
    /**
     * 消息发送类
     */
    private final MessageEmitter messageEmitter;
    /**
     * 绑定类
     */
    private final GimBind gimBind;
    /**
     * 集群类
     */
    private final ClusterRoute clusterRoute;

    /**
     * 业务监听
     */
    private ChannelBindListener channelBindListener;
    private ChannelStatusListener channelStatusListener;
    private ChannelAckListener channelAckListener;
    private ChannelReadListener channelReadListener;
    private OfflineMsgListener offlineMsgListener;


    /**
     * 实例一个连接容器用户保存TCP连接
     */
    private final ChannelGroup channels = new DefaultChannelGroup();
    /**
     * userId与ChannelId的映射关系，这里注意要使用线程安全的ConcurrentMap
     */
    private final ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap<>();
    /**
     * 群组成员映射，仅当非集群模式时保存群组映射
     */
    private final ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = new ConcurrentHashMap<>();

    /**
     * 业务处理器集合
     */
    private final Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    /**
     * 消息监听
     */
    private final ChatListener chatListener = new ChatListenerHandler(handlerMap);

    public GimContext(GimConfig gimConfig) {
        this.gimConfig = gimConfig;
        this.messageEmitter = new MessageEmitter(this);
        this.gimBind = new GimBind(this);
        this.clusterRoute = new ClusterRoute(this);

        //添加消息业务处理器
        handlerMap.put(ReqType.BIND_REQ, new BindHandler(this));
        handlerMap.put(ReqType.UNBIND_REQ, new BindHandler(this));
        handlerMap.put(ReqType.SINGLE_MSG_REQ, new SingleMsgHandler(this));
        handlerMap.put(ReqType.GROUP_MSG_REQ, new GroupMsgHandler(this));
        handlerMap.put(ReqType.ACK_REQ, new AckHandler(this));
        handlerMap.put(ReqType.HEART_BEAT_REQ, new HeartBeatHandler(this));
    }

    //-----------------------------------get----------------------------------------------


    public GimConfig getGimConfig() {
        return gimConfig;
    }

    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    public GimBind getGimBind() {
        return gimBind;
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

    public ChannelBindListener getChannelBindListener() {
        return channelBindListener;
    }

    public ChannelStatusListener getChannelStatusListener() {
        return channelStatusListener;
    }

    public ChannelAckListener getChannelAckListener() {
        return channelAckListener;
    }

    public ChannelReadListener getChannelReadListener() {
        return channelReadListener;
    }

    public OfflineMsgListener getOfflineMsgListener() {
        return offlineMsgListener;
    }

    public boolean isRun() {
        return run;
    }

    //-----------------------------------set----------------------------------------------

    public void setChannelBindListener(ChannelBindListener channelBindListener) {
        this.channelBindListener = channelBindListener;
    }

    public void setChannelStatusListener(ChannelStatusListener channelStatusListener) {
        this.channelStatusListener = channelStatusListener;
    }

    public void setChannelAckListener(ChannelAckListener channelAckListener) {
        this.channelAckListener = channelAckListener;
    }

    public void setChannelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
    }

    public void setOfflineMsgListener(OfflineMsgListener offlineMsgListener) {
        this.offlineMsgListener = offlineMsgListener;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
