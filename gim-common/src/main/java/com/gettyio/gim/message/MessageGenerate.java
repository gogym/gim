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
package com.gettyio.gim.message;

import com.gettyio.gim.comm.Const;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.packet.MessageClass.Message;
import com.gettyio.gim.packet.MessageInfo;
import com.gettyio.gim.utils.FastJsonUtils;
import com.gettyio.gim.utils.SnowflakeIdWorker;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.List;


/**
 * MessageGenerate.java
 *
 * @description:消息构造类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class MessageGenerate {

    private SnowflakeIdWorker idWorker;
    private String serverId;


    private static MessageGenerate messageGenerate;


    private MessageGenerate() {
    }

    private MessageGenerate(String serverId) {
        idWorker = new SnowflakeIdWorker(1, 1);
        this.serverId = serverId;
    }

    public static MessageGenerate getInstance(String serverId) {
        if (messageGenerate == null) {
            //保证异步处理安全操作
            synchronized (MessageGenerate.class) {
                if (messageGenerate == null) {
                    messageGenerate = new MessageGenerate(serverId);
                }
            }
        }
        return messageGenerate;
    }


    /**
     * MessageGenerate.java
     *
     * @description:构造消息
     * @author:gogym
     * @date:2020/4/10
     * @copyright: Copyright by gettyio.com
     */
    private Message.Builder CreateMessageBuilder(int reqType) {
        Message.Builder builder = Message.newBuilder();

        builder.setIdentify(Const.IDENTIFY);
        builder.setVersion(Const.VERSION);
        builder.setReqType(reqType);
        builder.setMsgTime(System.currentTimeMillis());
        builder.setId(String.valueOf(idWorker.nextId()));
        if (null != serverId) {
            builder.setSenderId(serverId);
        }
        return builder;
    }


    /**
     * 创建一个消息
     *
     * @param messageInfo
     * @return
     */
    public Message createMessage(MessageInfo messageInfo) {
        String json = FastJsonUtils.toJSONNoFeatures(messageInfo);
        Message.Builder builder = Message.newBuilder();
        try {
            JsonFormat.parser().merge(json, builder);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return builder.build();
    }


    /**
     * Description: 创建ack消息
     *
     * @param ack
     * @return
     * @see
     */
    public Message createAck(String ack) {
        Message.Builder builder = CreateMessageBuilder(Type.ACK_REQ);
        // 创建一个ack
        builder.setAck(ack);
        // 把ack消息放到消息body里
        return builder.build();
    }


    /**
     * Description: 创建心跳消息
     *
     * @return
     * @see
     */
    public Message createHeartBeat() {
        Message.Builder builder = CreateMessageBuilder(Type.HEART_BEAT_REQ);
        return builder.build();
    }


    /**
     * 创建用户绑定请求信息
     *
     * @param senderId
     * @return
     */
    public Message createBindReq(String senderId) {
        Message.Builder builder = CreateMessageBuilder(Type.BIND_REQ);
        builder.setSenderId(senderId);
        return builder.build();
    }


    /**
     * Description: 创建用户绑定响应信息
     *
     * @param senderId
     * @return
     * @see
     */
    public Message createBindResp(String senderId) {
        Message.Builder builder = CreateMessageBuilder(Type.BIND_RESP);
        builder.setSenderId(senderId);
        builder.setResult(Const.SUCCESS);
        builder.setBody("bind success");
        return builder.build();
    }


    /**
     * 解绑用户请求信息
     *
     * @param senderId
     * @return
     */
    public Message createUnbindReq(String senderId) {
        Message.Builder builder = CreateMessageBuilder(Type.UNBIND_REQ);
        builder.setSenderId(senderId);
        return builder.build();
    }

    /**
     * Description: 解绑用户响应信息
     *
     * @param senderId
     * @return
     * @see
     */
    public Message createUnbindResp(String senderId) {
        Message.Builder builder = CreateMessageBuilder(Type.UNBIND_RESP);
        builder.setSenderId(senderId);
        builder.setResult(Const.SUCCESS);
        builder.setBody("unbind success");
        return builder.build();
    }


    /**
     * 创建添加好友请求
     *
     * @return com.gettyio.gim.packet.MessageClass.Message
     * @params [senderId, receiverId, status]
     */
    public Message createAddFriendReq(String senderId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, String body) {
        Message.Builder builder = CreateMessageBuilder(Type.ADD_FRIEND_REQ);
        if (senderId != null) {
            builder.setSenderId(senderId);
        }
        if (senderName != null) {
            builder.setSenderName(senderName);
        }
        if (senderHeadImgUrl != null) {
            builder.setSenderHeadImgUrl(senderHeadImgUrl);
        }
        if (receiverId != null) {
            builder.setReceiverId(receiverId);
        }
        if (receiverName != null) {
            builder.setReceiverName(receiverName);
        }
        if (receiverHeadImgUrl != null) {
            builder.setReceiverHeadImgUrl(receiverHeadImgUrl);
        }
        if (body != null) {
            builder.setBody(body);
            builder.setBodyType(MessageContentType.text.getValue());
            builder.setBodyLength(body.length());
        }
        return builder.build();
    }

    /**
     * 创建好友添加响应
     *
     * @return com.gettyio.gim.packet.MessageClass.Message
     * @params [senderId, receiverId, status]
     */
    public Message createAddFriendResp(String senderId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, String body, Integer status) {
        Message.Builder builder = CreateMessageBuilder(Type.ADD_FRIEND_RESP);
        if (senderId != null) {
            builder.setSenderId(senderId);
        }
        if (senderName != null) {
            builder.setSenderName(senderName);
        }
        if (senderHeadImgUrl != null) {
            builder.setSenderHeadImgUrl(senderHeadImgUrl);
        }
        if (receiverId != null) {
            builder.setReceiverId(receiverId);
        }
        if (receiverName != null) {
            builder.setReceiverName(receiverName);
        }
        if (receiverHeadImgUrl != null) {
            builder.setReceiverHeadImgUrl(receiverHeadImgUrl);
        }
        if (body != null) {
            builder.setBody(body);
            builder.setBodyType(MessageContentType.text.getValue());
            builder.setBodyLength(body.length());
        }
        if (status != null) {
            builder.setStatus(status);
        }
        return builder.build();
    }


    /**
     * Description: 创建单聊消息
     *
     * @param senderId
     * @param receiverId
     * @param msgType
     * @param body
     * @return
     * @see
     */
    public Message createSingleChatReq(String senderId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, Integer msgType, String body, Integer bodyLength) {
        Message.Builder builder = CreateMessageBuilder(Type.SINGLE_MSG_REQ);
        if (senderId != null) {
            builder.setSenderId(senderId);
        }
        if (senderName != null) {
            builder.setSenderName(senderName);
        }
        if (senderHeadImgUrl != null) {
            builder.setSenderHeadImgUrl(senderHeadImgUrl);
        }
        if (receiverId != null) {
            builder.setReceiverId(receiverId);
        }
        if (receiverName != null) {
            builder.setReceiverName(receiverName);
        }
        if (receiverHeadImgUrl != null) {
            builder.setReceiverHeadImgUrl(receiverHeadImgUrl);
        }
        if (msgType != null) {
            builder.setBodyType(msgType);
        }

        if (body != null) {
            builder.setBody(body);
        }

        if (bodyLength != null) {
            builder.setBodyLength(bodyLength);
        }
        return builder.build();
    }

    /**
     * Description: 创建一个群消息
     *
     * @param sendlerId
     * @param groupId
     * @param msgType
     * @param body
     * @param atUserId
     * @return
     * @see
     */

    public Message createGroupChatReq(String sendlerId, String senderName, String senderHeadImgUrl, String groupId, String groupName, String groupHeadImgUrl, Integer msgType, String body, Integer bodyLength, List<String> atUserId) {

        Message.Builder builder = CreateMessageBuilder(Type.GROUP_MSG_REQ);

        if (sendlerId != null) {
            builder.setSenderId(sendlerId);
        }
        if (senderName != null) {
            builder.setSenderName(senderName);
        }
        if (senderHeadImgUrl != null) {
            builder.setSenderHeadImgUrl(senderHeadImgUrl);
        }
        if (groupId != null) {
            builder.setGroupId(groupId);
        }
        if (groupName != null) {
            builder.setGroupName(groupName);
        }
        if (groupHeadImgUrl != null) {
            builder.setGroupHeadImgUrl(groupHeadImgUrl);
        }
        if (msgType != null) {
            builder.setBodyType(msgType);
        }

        if (body != null) {
            builder.setBody(body);
        }

        if (bodyLength != null) {
            builder.setBodyLength(bodyLength);
        }

        if (atUserId != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String string : atUserId) {
                stringBuffer.append(string).append(",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            builder.setAtUserId(stringBuffer.toString());
        }
        return builder.build();
    }


}
