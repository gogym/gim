/*
 * 文件名：ConcentHandler.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
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
package com.gettyio.gim.handler.bshandler;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;


/**
 * GroupChatHandler.java
 *
 * @description:群聊处理器
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GroupMsgHandler extends AbsChatHandler<MessageClass.Message> {

    private GimContext gimContext;

    public GroupMsgHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message, SocketChannel socketChannel) throws Exception {
        //自动返回ack给客户端
        if (message.getReqType() != Type.ACK_REQ && message.getReqType() != Type.HEART_BEAT_REQ) {
            if (null != socketChannel) {
                //非集群消息socketChannel不为空
                MessageClass.Message ack = MessageGenerate.getInstance().createAck(message.getId());
                socketChannel.writeAndFlush(ack);
            } else {
                //集群过来的消息ack已经提前处理。无需在此处理
            }
        }

        // 接收者的ID
        String groupId = message.getToId();
        gimContext.messagEmitter.sendToGroup(groupId, message);

        if (gimContext.channelReadListener != null) {
            String msgJson = JsonFormat.printer().print(message);
            gimContext.channelReadListener.channelRead(msgJson);
        }

    }

}
