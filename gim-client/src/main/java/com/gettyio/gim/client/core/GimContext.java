
package com.gettyio.gim.client.core;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.client.bind.GimBind;
import com.gettyio.gim.client.comm.Type;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.handler.BaseChatHandler;
import com.gettyio.gim.client.handler.bshandler.*;
import com.gettyio.gim.client.listener.*;
import com.gettyio.gim.client.message.MessagEmitter;
import com.gettyio.gim.client.message.MessageDelayPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * 类名：GimContext.java
 * 描述：
 * 修改人：gogym
 * 时间：2020/3/18
 */
public class GimContext {

    public SocketChannel socketChannel;
    public GimBind gimBind;
    public MessagEmitter messagEmitter;
    public GimConfig gimConfig;

    public ChannelBindListener channelBindListener;
    public ChannelStatusListener channelStatusListener;
    public ChannelAckListener channelAckListener;
    public ChannelReadListener channelReadListener;
    public ChannelWriteListener channelWriteListener;
    public ChannelWriteFailListener channelWriteFailListener;


    // 保存已经写到客户端，但未收到ack的msg,通过延迟队列重发
    public DelayQueue<MessageDelayPacket> delayMsgQueue = new DelayQueue<MessageDelayPacket>();
    // 业务处理器集合
    public Map<Integer, AbsChatHandler<?>> handlerMap = new HashMap<>();
    //消息监听
    public ChatListener chatListener = new BaseChatHandler(handlerMap);

    public GimContext(GimConfig gimConfig, ChannelStatusListener channelStatusListener) {
        this.gimConfig = gimConfig;
        this.messagEmitter = new MessagEmitter(this);
        this.gimBind = new GimBind(this);
        this.channelStatusListener = channelStatusListener;

        //添加消息处理器
        handlerMap.put(Type.ACK_REQ, new AckHandler(this));
        handlerMap.put(Type.CONNET_RESP, new ConnectHandler(this));
        handlerMap.put(Type.SINGLE_MSG_REQ, new SingleChatHandler(this));
        handlerMap.put(Type.GROUP_MSG_REQ, new GroupChatHandler(this));
        handlerMap.put(Type.ADD_FRIEND_RESP, new AddFriendRespHandler(this));
        handlerMap.put(Type.ADD_FRIEND_REQ, new AddFriendReqHandler(this));

    }


    public GimContext channelReadListener(ChannelReadListener channelReadListener) {
        this.channelReadListener = channelReadListener;
        return this;
    }

    public GimContext channelWriteListener(ChannelWriteListener channelWriteListener) {
        this.channelWriteListener = channelWriteListener;
        return this;
    }

    public GimContext channelWriteFailListener(ChannelWriteFailListener channelWriteFailListener) {
        this.channelWriteFailListener = channelWriteFailListener;
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


}
