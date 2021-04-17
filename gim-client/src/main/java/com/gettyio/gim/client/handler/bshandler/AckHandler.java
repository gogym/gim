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
package com.gettyio.gim.client.handler.bshandler;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;

import java.util.Iterator;

/**
 * AckHandler.java
 *
 * @description:ack处理器
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class AckHandler extends AbsChatHandler<MessageClass.Message> {

    private GimContext gimContext;

    public AckHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message, SocketChannel socketChannel) throws Exception {
        //回调监听
        final String ack = message.getAck();
        if (gimContext.getChannelAckListener() != null) {
            gimContext.getChannelAckListener().onAck(ack);
        }

        //兼容jdk1.7,清理发送成功的缓存消息
        final Iterator<MessageDelayPacket> each = gimContext.getDelayMsgQueue().iterator();
        while (each.hasNext()) {
            MessageClass.Message msg = each.next().getMessage();
            if (msg.getId().equals(ack)) {
                each.remove();
            }
        }

    }
}
