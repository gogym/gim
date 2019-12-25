/*
 * 文件名：ChatContext.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月10日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.server;

import com.gettyio.core.channel.group.ChannelGroup;
import com.gettyio.core.channel.group.DefaultChannelGroup;
import com.gettyio.gim.bind.GimBind;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.handler.BaseChatHandler;
import com.gettyio.gim.listener.ChatListener;
import com.gettyio.gim.listener.GimListener;
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
    //回调监听
    public GimListener gimListener;
    //消息发送类
    public MessagEmitter messagEmitter;
    //绑定类
    public GimBind gimBind;

    // 实例一个连接容器用户保存TCP连接
    public ChannelGroup channels = new DefaultChannelGroup();
    // userId与ChannelId的映射关系，这里注意要使用线程安全的ConcurrentMap
    public ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap<>();
    // 群组成员映射
    public ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<String>>();
    // 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
    public DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<MessageDelayPacket>();
    // 业务处理器集合
    public Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    //消息监听
    public ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig, GimListener gimListener) {
        this.gimConfig = gimConfig;
        this.messagEmitter = new MessagEmitter(this);
        this.gimListener = gimListener;
        this.gimBind = new GimBind(this);
    }


}
