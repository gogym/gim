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
package com.gettyio.gim.client.expansion;

import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;

/**
 * DelayMsgQueueListener.java
 *
 * @description:延迟消息监听
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class DelayMsgQueueListener implements Runnable {

    private GimContext gimContext;

    public DelayMsgQueueListener(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    public void takeMessage(GimContext gimContext) {
        try {
            // 从代发队列中拿出消息
            MessageDelayPacket element = gimContext.delayMsgQueue.take();
            if (element.getNum() >= gimContext.gimConfig.getReWriteNum()) {
                //超过重发次数，提示发送失败
                if (gimContext.channelReSendListener != null) {
                    gimContext.channelReSendListener.onFail(element.getMessage());
                }
            } else {
                MessageClass.Message msg = element.getMessage();
                gimContext.messagEmitter.sendOnly(msg);
                //重发后重新加入队列，等待下一次重发
                element.incrNum();
                element.setDelay(element.getOriginalDelay());
                gimContext.delayMsgQueue.put(element);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            takeMessage(gimContext);
        }
    }
}
