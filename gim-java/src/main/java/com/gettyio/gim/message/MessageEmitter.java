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
import com.gettyio.expansion.handler.codec.websocket.frame.BinaryWebSocketFrame;
import com.gettyio.gim.comm.Const;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.comm.SocketType;
import com.google.protobuf.util.JsonFormat;

import java.util.HashSet;
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
public class MessageEmitter {

    private GimContext gimContext;

    public MessageEmitter(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * 发送消息，会加入重写确认，不在线则会离线
     *
     * @param toId
     * @param msg
     * @see
     */
    private void send(String toId, MessageClass.Message msg, boolean offline) throws Exception {
        //查找目标链接id
        String channelId = gimContext.getUserChannelMap().get(toId);

        if (channelId != null) {
            //查找连接
            SocketChannel channel = gimContext.getChannels().find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null]");
            }
            //判断通道类型
            if (((int) channel.getChannelAttribute(Const.SOCKET_TYPE_KEY)) == SocketType.WEB_SOCKET) {
                BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(msg.toByteArray());
                channel.writeAndFlush(binaryWebSocketFrame);
            } else {
                channel.writeAndFlush(msg);
            }
            return;
        } else if (gimContext.getGimConfig().isEnableCluster()) {
            //通过目标id查找目标服务id
            String serverId = gimContext.getClusterRoute().getRoute(toId);
            if (serverId != null) {
                //发送到集群
                gimContext.getClusterRoute().sendToCluster(msg, serverId, toId);
                return;
            }
        }

        //如果找不到连接，则离线处理
        if (offline && gimContext.getOfflineMsgListener() != null) {
            //离线消息
            String msgJson = JsonFormat.printer().print(msg);
            gimContext.getOfflineMsgListener().onMsg(msgJson);

            if (msg.getReqType() == Type.SINGLE_MSG_REQ) {
                String fromId = msg.getFromId();
                //单聊要构造ack消息回复来源端，避免来源端得不到应答,收发位置要对调
                MessageClass.Message ack = MessageGenerate.getInstance().createAck(toId, fromId, msg.getId());
                send(fromId, ack, false);
            }
        }
    }


    /**
     * 单纯发送给用户,不重写也不离线
     *
     * @param toId
     * @param msg
     * @return void
     */
    private void sendOnly(String toId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.getUserChannelMap().get(toId);
        if (channelId != null) {
            SocketChannel channel = gimContext.getChannels().find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null]");
            }

            if (((int) channel.getChannelAttribute(Const.SOCKET_TYPE_KEY)) == SocketType.WEB_SOCKET) {
                BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(msg.toByteArray());
                channel.writeAndFlush(binaryWebSocketFrame);
            } else {
                channel.writeAndFlush(msg);
            }
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------


    /**
     * 发送给单个目标
     *
     * @param toId
     * @param msg
     * @throws Exception
     */
    public void sendToSingle(String toId, MessageClass.Message msg) throws Exception {
        send(toId, msg, true);
    }

    /**
     * 发送给单个目标
     *
     * @param toId
     * @param msg
     * @throws Exception
     */
    public void sendToSingleNotOffline(String toId, MessageClass.Message msg) throws Exception {
        send(toId, msg, false);
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

        if (null != msg.getToId() && !"".equals(msg.getToId())) {
            //如果消息目标ID不等于空，则这条消息是通过集群路由过来的。此时应直接在本机处理
            sendToSingle(msg.getToId(), msg);
        } else {
            Set<String> set = new HashSet<>();
            // 先判断是否开启集群
            if (gimContext.getGimConfig().isEnableCluster()) {
                Set<String> groupSet = gimContext.getClusterRoute().getGroupRoute(groupId);
                if (groupSet != null) {
                    set.addAll(groupSet);
                }
            } else {
                CopyOnWriteArrayList<String> list = gimContext.getGroupUserMap().get(groupId);
                if (list != null) {
                    set.addAll(list);
                }
            }

            //群信息不转发给发送者，因此先将发送者移除
            set.remove(msg.getFromId());
            for (String string : set) {
                sendToSingle(string, msg);
            }

        }
    }


    /**
     * 发送绑定消息成功结果
     *
     * @return void
     */
    public void sendBindResp(String toId) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance().createBindResp(toId);
        sendOnly(toId, msg);
    }

    /**
     * 发送解绑消息成功结果
     *
     * @param toId
     * @throws Exception
     */
    public void sendUnbindResp(String toId) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance().createUnbindResp(toId);
        sendOnly(toId, msg);
    }


    /**
     * 发送单聊消息
     *
     * @return void
     */
    public void sendSingleMsg(String fromId, String toId, String body) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance().createSingleMsgReq(fromId, toId, body);
        sendToSingle(toId, msg);
    }


    /**
     * 发送群聊消息
     *
     * @return void
     */
    public void sendGroupMsg(String fromId, String groupId, String body) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance().createGroupMsgReq(fromId, groupId, body);
        sendToGroup(groupId, msg);
    }


}
