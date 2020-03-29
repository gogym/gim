package com.gettyio.gim.client.handler.bshandler;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.message.MessageDelayPacket;
import com.gettyio.gim.client.packet.MessageClass;

import java.util.Iterator;


/**
 * ack处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see AckHandler
 * @since
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
        if (gimContext.channelAckListener != null) {
            gimContext.channelAckListener.ack(ack);
        }


        //兼容jdk1.7
        final Iterator<MessageDelayPacket> each = gimContext.delayMsgQueue.iterator();
        while (each.hasNext()) {
            if (each.next().getMessage().getId().equals(ack)) {
                each.remove();
            }
        }

//        gimContext.delayMsgQueue.removeIf(t -> {
//            // 如果存在，从重发队列中移除消息
//            return t.getMessage().getId().equals(ack);
//        });
    }
}
