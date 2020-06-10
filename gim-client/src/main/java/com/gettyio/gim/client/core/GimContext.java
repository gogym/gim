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
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.handler.BaseChatHandler;
import com.gettyio.gim.client.handler.ChatListener;
import com.gettyio.gim.client.handler.bshandler.*;
import com.gettyio.gim.client.listener.*;
import com.gettyio.gim.client.emitter.MessagEmitter;
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
    public SocketChannel socketChannel;
    /**
     * 绑定类
     */
    public GimBind gimBind;
    /**
     * 发送类
     */
    public MessagEmitter messagEmitter;
    /**
     * 配置类
     */
    public GimConfig gimConfig;

    /**
     * 业务监听
     */
    public ChannelBindListener channelBindListener;
    public ChannelUnBindListener channelUnBindListener;
    public ChannelStatusListener channelStatusListener;
    public ChannelAckListener channelAckListener;
    public ChannelReadListener channelReadListener;
    public ChannelWriteListener channelWriteListener;
    public ChannelReSendListener channelReSendListener;


    /**
     * 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
     */
    public DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<MessageDelayPacket>();
    /**
     * 业务处理器集合
     */
    public Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    /**
     * 消息监听
     */
    public ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig, ChannelStatusListener channelStatusListener) {
        this.gimConfig = gimConfig;
        this.messagEmitter = new MessagEmitter(this);
        this.gimBind = new GimBind(this);
        this.channelStatusListener = channelStatusListener;

        //添加消息处理器
        handlerMap.put(Type.ACK_REQ, new AckHandler(this));
        handlerMap.put(Type.BIND_RESP, new BindHandler(this));
        handlerMap.put(Type.UNBIND_RESP, new BindHandler(this));
        handlerMap.put(Type.SINGLE_MSG_REQ, new SingleMsgHandler(this));
        handlerMap.put(Type.GROUP_MSG_REQ, new GroupMsgHandler(this));
        handlerMap.put(Type.SINGLE_VIDEO_CHAT_REQ, new SingleVideoHandler(this));
        handlerMap.put(Type.OTHER_REQ, new OtherMsgHandler(this));
    }


    public GimContext channelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
        return this;
    }

    public GimContext channelWriteListener(ChannelWriteListener channelWriteListener) {
        this.channelWriteListener = channelWriteListener;
        return this;
    }

    public GimContext channelReSendListener(ChannelReSendListener channelReSendListener) {
        this.channelReSendListener = channelReSendListener;
        return this;
    }

    public GimContext channelAckListener(ChannelAckListener channelAckListener) {
        this.channelAckListener = channelAckListener;
        return this;
    }


}
