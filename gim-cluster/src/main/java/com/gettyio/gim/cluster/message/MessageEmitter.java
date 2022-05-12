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
package com.gettyio.gim.cluster.message;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.expansion.handler.codec.websocket.frame.BinaryWebSocketFrame;
import com.gettyio.gim.cluster.server.GimContext;
import com.gettyio.gim.comm.Const;
import com.gettyio.gim.comm.SocketType;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;


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
        } else if (gimContext.getClusterConfig().isEnableCluster()) {
            //通过目标id查找目标服务id
            String serverId = gimContext.getClusterRoute().getRoute(toId);
            if (serverId != null) {
                //发送到集群
                gimContext.getClusterRoute().sendToCluster(msg, serverId);
                return;
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
     * 发送单聊消息
     *
     * @return void
     */
    public void sendSingleMsg(String fromId, String toId, String body) throws Exception {
        MessageClass.Message msg = MessageGenerate.getInstance().createSingleMsgReq(fromId, toId, body);
        sendToSingle(toId, msg);
    }




}
