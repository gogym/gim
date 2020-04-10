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
package com.gettyio.gim.client.message;

import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.message.MessageContentType;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.List;

/**
 * MessagEmitter.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class MessagEmitter {

    private GimContext gimContext;

    public MessagEmitter(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * Description: send msg to server
     *
     * @param msg
     * @see
     */
    public void send(MessageClass.Message msg) {

        if (gimContext.gimConfig.isAutoRewrite()) {
            //如果开启了重发
            MessageDelayPacket mdp = new MessageDelayPacket(msg, gimContext.gimConfig.getReWriteDelay());
            gimContext.delayMsgQueue.put(mdp);
        }
        //注意，要在加入重发队列后在发到服务器。否则ACK返回后，还没有加入到队列，就会造成一次无意义的重发
        sendNoCallBack(msg);

        //发送消息回调
        try {
            String msgJson = JsonFormat.printer().print(msg);
            if (gimContext.channelWriteListener != null) {
                gimContext.channelWriteListener.onWrite(msgJson);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }


    /**
     * 发送，但不回调
     *
     * @param msg
     */
    public void sendNoCallBack(MessageClass.Message msg) {
        if (!gimContext.socketChannel.isInvalid()) {
            gimContext.socketChannel.writeAndFlush(msg);
        }
    }


    /**
     * 发送心跳消息
     *
     * @return void
     * @params []
     */
    public void sendHeartBeat() {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createHeartBeat();
        sendNoCallBack(msg);
    }


    /**
     * 发送用户绑定消息
     *
     * @return void
     * @params [userId]
     */
    public void sendBindReq(String userId) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createBindReq(userId);
        sendNoCallBack(msg);
    }

    /**
     * 发送用户解绑消息
     *
     * @return void
     * @params [userId]
     */
    public void sendUnbindReq(String userId) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createUnbindReq(userId);
        sendNoCallBack(msg);
    }


    /**
     * 发送单聊文本消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendSingleChatText(String sendlerId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, String text) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createSingleChatReq(sendlerId, senderName, senderHeadImgUrl, receiverId, receiverName, receiverHeadImgUrl, MessageContentType.text.getValue(), text);
        send(msg);
    }


    /**
     * 发送单聊图片消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendSingleChatImg(String sendlerId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, String path) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createSingleChatReq(sendlerId, senderName, senderHeadImgUrl, receiverId, receiverName, receiverHeadImgUrl, MessageContentType.image.getValue(), path);
        send(msg);
    }


    /**
     * 发送单聊语音消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendSingleChatAudio(String sendlerId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, String audioBase64) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createSingleChatReq(sendlerId, senderName, senderHeadImgUrl, receiverId, receiverName, receiverHeadImgUrl, MessageContentType.audio.getValue(), audioBase64);
        send(msg);
    }

    /**
     * 发送群聊文本消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendGroupChatText(String sendlerId, String senderName, String senderHeadImgUrl, String groupId, String groupName, String groupHeadImgUrl, String text, List<String> atUserId) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createGroupChatReq(sendlerId, senderName, senderHeadImgUrl, groupId, groupName, groupHeadImgUrl, MessageContentType.text.getValue(), text, atUserId);
        send(msg);
    }


    /**
     * 发送群聊图片消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendGroupChatImg(String sendlerId, String senderName, String senderHeadImgUrl, String groupId, String groupName, String groupHeadImgUrl, String path) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createGroupChatReq(sendlerId, senderName, senderHeadImgUrl, groupId, groupName, groupHeadImgUrl, MessageContentType.image.getValue(), path, null);
        send(msg);
    }


    /**
     * 发送群聊语音消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendGroupChatAudio(String sendlerId, String senderName, String senderHeadImgUrl, String groupId, String groupName, String groupHeadImgUrl, String audioBase64) {
        MessageClass.Message msg = MessageGenerate.getInstance(null).createGroupChatReq(sendlerId, senderName, senderHeadImgUrl, groupId, groupName, groupHeadImgUrl, MessageContentType.audio.getValue(), audioBase64, null);
        send(msg);
    }


}
