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
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;

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
        String ack = message.getAck();
        if (gimContext.channelAckListener != null) {
            gimContext.channelAckListener.onAck(ack);
        }

        //兼容jdk1.7
        final Iterator<MessageDelayPacket> each = gimContext.delayMsgQueue.iterator();
        while (each.hasNext()) {
            if (each.next().getMessage().getId().equals(ack)) {
                each.remove();
            }
        }


//        gimContext.delayMsgQueue.removeIf(new Predicate<MessageDelayPacket>() {
//            @Override
//            public boolean test(MessageDelayPacket t) {
//                // 如果存在，从队列中移除消息
//                return t.getMessage().getId().equals(ack);
//            }
//        });
    }


}
