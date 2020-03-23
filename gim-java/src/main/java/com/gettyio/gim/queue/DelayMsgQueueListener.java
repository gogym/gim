package com.gettyio.gim.queue;


import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;

public class DelayMsgQueueListener implements Runnable {

    private GimContext gimContext;

    public DelayMsgQueueListener(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    public void takeMessage() {
        try {
            // 从代发队列中拿出消息
            MessageDelayPacket element = gimContext.delayMsgQueue.take();
            if (element.getNum() >= gimContext.gimConfig.getReWriteNum()) {
                //超过重发次数后，离线消息
                if (gimContext.offlineMsgIntf != null) {
                    gimContext.offlineMsgIntf.offlineMsg(element.toJson());
                }
            } else {
                MessageClass.Message msg = element.getMessage();
                gimContext.messagEmitter.sendToUserNoReWrite(msg.getReceiverId(), msg);
                //等待下一次重发
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
            takeMessage();
        }
    }
}
