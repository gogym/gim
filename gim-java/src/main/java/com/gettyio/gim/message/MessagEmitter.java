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

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.comm.Const;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.packet.MessageInfo;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * MessagEmitter.java
 *
 * @description:消息发送类
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
     * 发送消息，会加入重写确认，离线
     *
     * @param userId
     * @param msg
     * @see
     */
    public void sendToUser(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);

        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            //放入重发队列中
            MessageDelayPacket mdp = new MessageDelayPacket(msg, Const.MSG_DELAY);
            gimContext.delayMsgQueue.put(mdp);

            channel.writeAndFlush(msg);
            return;
        } else if (gimContext.gimConfig.isEnableCluster()) {
            String serverId = gimContext.clusterRoute.getUserRoute(userId);
            if (serverId != null) {
                MessageDelayPacket mdp = new MessageDelayPacket(msg, Const.MSG_DELAY);
                gimContext.delayMsgQueue.put(mdp);
                //查找服务路由
                gimContext.clusterRoute.sendToCluster(msg, serverId);
                return;
            }

        }

        //如果找不到连接，则离线处理
        if (gimContext.offlineMsgListener != null) {
            //离线消息
            String msgJson = JsonFormat.printer().print(msg);
            gimContext.offlineMsgListener.onMsg(msgJson);
        }
    }


    /**
     * 发送但不加入重写队列
     *
     * @param userId
     * @param msg
     * @return void
     */
    public void sendToUserNoReWrite(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);

        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            channel.writeAndFlush(msg);
            return;
        } else if (gimContext.gimConfig.isEnableCluster()) {
            String serverId = gimContext.clusterRoute.getUserRoute(userId);
            if (serverId != null) {
                //查找服务路由
                gimContext.clusterRoute.sendToCluster(msg, serverId);
                return;
            }

        }

        if (gimContext.offlineMsgListener != null) {
            //离线消息
            String msgJson = JsonFormat.printer().print(msg);
            gimContext.offlineMsgListener.onMsg(msgJson);
        }
    }


    /**
     * 单纯发送给用户,不重写也不离线
     *
     * @param userId
     * @param msg
     * @return void
     */
    public void sendToUserOnly(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);
        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            channel.writeAndFlush(msg);
        }
    }


    /**
     * 发到一个群
     *
     * @param groupId
     * @param msg
     * @throws Exception
     * @see
     */
    public void sendToGroup(String groupId, MessageClass.Message msg) throws Exception {

        if (null != msg.getServerId() && !"".equals(msg.getServerId())) {
            //如果消息服务器ID不等于空，则这条消息是通过集群路由过来的。此时应直接在本机处理
            sendToUser(msg.getReceiverId(), msg);
        } else {
            // 先判断是否开启集群
            if (gimContext.gimConfig.isEnableCluster()) {
                Set<String> set = gimContext.clusterRoute.getGroupRoute(groupId);
                //群信息不转发给发送者，因此先将发送者移除
                set.remove(msg.getSenderId());
                if (set != null) {
                    for (String string : set) {
                        //发送时把群消息接收者ID设置进去，表示这条信息是给这个人的
                        MessageClass.Message.Builder builder = msg.toBuilder().setReceiverId(string);
                        sendToUser(string, builder.build());
                    }
                }
            } else {
                CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
                if (list != null) {
                    for (String string : list) {
                        if (msg.getSenderId().equals(string)) {
                            //群信息不转发给发送者
                            continue;
                        }
                        //发送时把群消息接收者ID设置进去
                        MessageClass.Message.Builder builder = msg.toBuilder().setReceiverId(string);
                        sendToUser(string, builder.build());
                    }
                }
            }
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------

    /**
     * 发送用户绑定消息成功结果
     *
     * @return void
     * @params [userId]
     */
    public void sendBindResp(String userId) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance(gimContext.gimConfig.getServerId()).createBindResp(userId);
        sendToUserOnly(userId, msg);
    }

    /**
     * 发送用户解绑消息成功结果
     *
     * @param userId
     * @throws Exception
     */
    public void sendUnbindResp(String userId) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance(gimContext.gimConfig.getServerId()).createUnbindResp(userId);
        sendToUserOnly(userId, msg);
    }


    /**
     * 发送单聊消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendSingleChatMsg(String sendlerId, String senderName, String senderHeadImgUrl, String receiverId, String receiverName, String receiverHeadImgUrl, Integer bodyType, String body, Integer bodyLength) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance(gimContext.gimConfig.getServerId()).createSingleChatReq(sendlerId, senderName, senderHeadImgUrl, receiverId, receiverName, receiverHeadImgUrl, bodyType, body, bodyLength);
        sendToUser(receiverId, msg);
    }


    /**
     * 发送群聊消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendGroupChatMsg(String sendlerId, String senderName, String senderHeadImgUrl, String groupId, String groupName, String groupHeadImgUrl, Integer bodyType, String body, Integer bodyLength, List<String> atUserId) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance(gimContext.gimConfig.getServerId()).createGroupChatReq(sendlerId, senderName, senderHeadImgUrl, groupId, groupName, groupHeadImgUrl, bodyType, body, bodyLength, atUserId);
        sendToGroup(groupId, msg);
    }


    /**
     * 发送自定消息
     *
     * @param messageInfo
     * @throws Exception
     */
    public void sendMessageToUser(MessageInfo messageInfo) throws Exception {
        if (null != messageInfo) {
            MessageClass.Message msg = MessageGenerate.getInstance(null).createMessage(messageInfo);
            sendToUser(messageInfo.getReceiverId(), msg);
        }
    }

    /**
     * 发送自定消息到群组
     *
     * @param messageInfo
     * @throws Exception
     */
    public void sendMessageToGroup(MessageInfo messageInfo) throws Exception {
        if (null != messageInfo) {
            MessageClass.Message msg = MessageGenerate.getInstance(null).createMessage(messageInfo);
            sendToUser(messageInfo.getGroupId(), msg);
        }
    }
}
