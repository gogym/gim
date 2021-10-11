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
package com.gettyio.gim.client.emitter;

import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.listener.ChannelWriteListener;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;

/**
 * MessagEmitter.java
 *
 * @description:
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
     * Description: send msg to server
     *
     * @param msg
     * @see
     */
    public void send(MessageClass.Message msg, ChannelWriteListener channelWriteListener) {
        send(msg);
        //发送消息回调
        if (channelWriteListener != null) {
            channelWriteListener.onWrite(msg);
        }
    }


    public void send(MessageClass.Message msg) {

        //注意，要在加入重发队列后在发到服务器。否则ACK返回后，还没有加入到队列，就会造成一次无意义的重发
        if (gimContext.getGimConfig().isAutoRewrite()) {
            //如果开启了重发
            MessageDelayPacket mdp = new MessageDelayPacket(msg, gimContext.getGimConfig().getReWriteDelay());
            gimContext.getDelayMsgQueue().put(mdp);
        }
        //注意，要在加入重发队列后在发到服务器。否则ACK返回后，还没有加入到队列，就会造成一次无意义的重发
        sendOnly(msg);
    }

    /**
     * 仅仅发送，不回调也不加入重发队列
     *
     * @param msg
     */
    public void sendOnly(MessageClass.Message msg) {
        if (!gimContext.getSocketChannel().isInvalid()) {
            gimContext.getSocketChannel().writeAndFlush(msg);
        }
    }
}
