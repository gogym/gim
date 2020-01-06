/*
 * 文件名：DelayMsgQueueListener.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.listener.queue;


import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.client.message.MessageDelayPacket;
import com.gettyio.gim.client.packet.MessageClass;

public class DelayMsgQueueListener {

    public static void takeMessage(GimContext gimContext) {

        try {
            // 从代发队列中拿出消息
            MessageDelayPacket element = gimContext.delayMsgQueue
                    .take();
            MessageClass.Message msg = element.getMessage();
            gimContext.messagEmitter.sendToServer(element.getUserId(), msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
