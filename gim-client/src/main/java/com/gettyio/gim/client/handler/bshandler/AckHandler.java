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

package com.gettyio.gim.client.handler.bshandler;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.message.MessageDelayPacket;
import com.gettyio.gim.client.packet.AckReqClass;
import com.gettyio.gim.client.packet.MessageClass;

import java.util.function.Predicate;


/**
 * ack处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see AckHandler
 * @since
 */
public class AckHandler extends AbsChatHandler<AckReqClass.AckReq> {

    private GimContext gimContext;

    public AckHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<AckReqClass.AckReq> bodyClass() {
        return AckReqClass.AckReq.class;
    }

    @Override
    public void handler(MessageClass.Message message, AckReqClass.AckReq bsBody, AioChannel aioChannel) throws Exception {

        String ack = bsBody.getAck();

        gimContext.delayMsgQueue.removeIf(new Predicate<MessageDelayPacket>() {
            @Override
            public boolean test(MessageDelayPacket t) {
                // 如果存在，从队列中移除消息
                return t.getMessage().getId().equals(ack);
            }
        });

    }
}
