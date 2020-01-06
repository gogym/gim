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

package com.gettyio.gim.client.client;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.core.channel.group.ChannelGroup;
import com.gettyio.core.channel.group.DefaultChannelGroup;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.handler.BaseChatHandler;
import com.gettyio.gim.client.listener.ChatListener;
import com.gettyio.gim.client.listener.GimListener;
import com.gettyio.gim.client.message.MessagEmitter;
import com.gettyio.gim.client.message.MessageDelayPacket;

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

    public AioChannel aioChannel;

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
    }


}
