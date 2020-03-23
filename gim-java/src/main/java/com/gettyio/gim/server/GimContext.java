package com.gettyio.gim.server;

import com.gettyio.core.channel.group.ChannelGroup;
import com.gettyio.core.channel.group.DefaultChannelGroup;
import com.gettyio.gim.bind.GimBind;
import com.gettyio.gim.cluster.ClusterRoute;
import com.gettyio.gim.common.Const;
import com.gettyio.gim.common.Type;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.handler.BaseChatHandler;
import com.gettyio.gim.handler.bshandler.*;
import com.gettyio.gim.intf.OfflineMsgIntf;
import com.gettyio.gim.listener.*;
import com.gettyio.gim.message.MessagEmitter;
import com.gettyio.gim.message.MessageDelayPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.DelayQueue;

public class GimContext {

    public GimConfig gimConfig;
    //消息发送类
    public MessagEmitter messagEmitter;
    //绑定类
    public GimBind gimBind;


    public ChannelBindListener channelBindListener;
    public ChannelStatusListener channelStatusListener;
    public ChannelAckListener channelAckListener;
    public ChannelReadListener channelReadListener;

    public OfflineMsgIntf offlineMsgIntf;

    //集群配置
    public ClusterRoute clusterRoute;


    // 实例一个连接容器用户保存TCP连接
    public ChannelGroup channels = new DefaultChannelGroup();
    // userId与ChannelId的映射关系，这里注意要使用线程安全的ConcurrentMap
    public ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap<>();
    // 群组成员映射
    public ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = new ConcurrentHashMap<>();
    // 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
    public DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<>();
    // 业务处理器集合
    public Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    //消息监听
    public ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig) {
        //设置服务器标志，对集群很重要
        Const.serverId = gimConfig.getServerId();
        this.gimConfig = gimConfig;
        this.messagEmitter = new MessagEmitter(this);
        this.gimBind = new GimBind(this);
        this.clusterRoute = new ClusterRoute(this);

        //添加消息处理器
        handlerMap.put(Type.ACK_REQ, new AckHandler(this));
        handlerMap.put(Type.CONNET_REQ, new ConnectHandler(this));
        handlerMap.put(Type.HEART_BEAT_REQ, new HeartBeatHandler());
        handlerMap.put(Type.SINGLE_MSG_REQ, new SingleChatHandler(this));
        handlerMap.put(Type.GROUP_MSG_REQ, new GroupChatHandler(this));
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

    public GimContext offlineMsgIntf(OfflineMsgIntf offlineMsgIntf) {
        this.offlineMsgIntf = offlineMsgIntf;
        return this;
    }

    public ClusterRoute getClusterRoute() {
        return clusterRoute;
    }
}
