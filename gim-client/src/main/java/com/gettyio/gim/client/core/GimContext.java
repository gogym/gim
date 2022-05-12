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
package com.gettyio.gim.client.core;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.client.bind.GimBind;
import com.gettyio.gim.comm.ReqType;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.handler.BaseChatHandler;
import com.gettyio.gim.client.handler.ChatListener;
import com.gettyio.gim.client.handler.bshandler.*;
import com.gettyio.gim.client.listener.*;
import com.gettyio.gim.client.emitter.MessageEmitter;
import com.gettyio.gim.message.MessageDelayPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;


/**
 * GimContext.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimContext {

    /**
     * 连接通道
     */
    private SocketChannel socketChannel;
    /**
     * 绑定类
     */
    private final GimBind gimBind;
    /**
     * 发送类
     */
    private final MessageEmitter messageEmitter;
    /**
     * 配置类
     */
    private final GimConfig gimConfig;

    /**
     * 业务监听
     */

    private ChannelStatusListener channelStatusListener;
    private ChannelAckListener channelAckListener;
    private ChannelReadListener channelReadListener;
    private ChannelReSendListener channelReSendListener;


    /**
     * 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
     */
    private final DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<MessageDelayPacket>();
    /**
     * 业务处理器集合
     */
    private final Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    /**
     * 消息监听
     */
    private final ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig) {
        this.gimConfig = gimConfig;
        this.messageEmitter = new MessageEmitter(this);
        this.gimBind = new GimBind(this);

        //添加消息处理器
        handlerMap.put(ReqType.BIND_RESP, new BindHandler(this));
        handlerMap.put(ReqType.UNBIND_RESP, new BindHandler(this));
        handlerMap.put(ReqType.SINGLE_MSG_REQ, new SingleMsgHandler(this));
        handlerMap.put(ReqType.GROUP_MSG_REQ, new GroupMsgHandler(this));
        handlerMap.put(ReqType.ACK_REQ, new AckHandler(this));
    }


    //--------------------------------------get---------------------------------------------


    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public GimBind getGimBind() {
        return gimBind;
    }

    public MessageEmitter getMessageEmitter() {
        return messageEmitter;
    }

    public GimConfig getGimConfig() {
        return gimConfig;
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

    public ChannelReSendListener getChannelReSendListener() {
        return channelReSendListener;
    }

    public DelayQueue<MessageDelayPacket> getDelayMsgQueue() {
        return delayMsgQueue;
    }

    public Map<Integer, AbsChatHandler<?>> getHandlerMap() {
        return handlerMap;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }


    //--------------------------------------get---------------------------------------------


    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void setChannelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
    }

    public void setChannelReSendListener(ChannelReSendListener channelReSendListener) {
        this.channelReSendListener = channelReSendListener;
    }

    public void setChannelAckListener(ChannelAckListener channelAckListener) {
        this.channelAckListener = channelAckListener;
    }

    public void setChannelStatusListener(ChannelStatusListener channelStatusListener) {
        this.channelStatusListener = channelStatusListener;
    }

}
