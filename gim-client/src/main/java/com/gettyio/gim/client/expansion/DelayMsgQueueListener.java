package com.gettyio.gim.client.expansion;

import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.message.MessageDelayPacket;
import com.gettyio.gim.client.packet.MessageClass;

public class DelayMsgQueueListener {

    public static void takeMessage(GimContext gimContext) {
        try {
            // 从代发队列中拿出消息
            MessageDelayPacket element = gimContext.delayMsgQueue.take();
            if (element.getNum() >= gimContext.gimConfig.getReWriteNum()) {
                //超过重发次数，提示发送失败
                if (gimContext.channelWriteFailListener != null) {
                    gimContext.channelWriteFailListener.reWriteFail(element.toJson());
                }
            } else {

                MessageClass.Message msg = element.getMessage();
                gimContext.messagEmitter.sendNoCallBack(msg);
                //重发后重新加入队列，等待下一次重发
                element.incrNum();
                element.setDelay(element.getOriginalDelay());
                gimContext.delayMsgQueue.put(element);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
