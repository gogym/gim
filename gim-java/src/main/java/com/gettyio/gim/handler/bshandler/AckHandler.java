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

package com.gettyio.gim.handler.bshandler;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageDelayPacket;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;

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
        String ack = message.getAck();
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


//        gimContext.delayMsgQueue.removeIf(new Predicate<MessageDelayPacket>() {
//            @Override
//            public boolean test(MessageDelayPacket t) {
//                // 如果存在，从队列中移除消息
//                return t.getMessage().getId().equals(ack);
//            }
//        });
    }


}
