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


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessagEmitter;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.packet.SingleChatReqClass;
import com.gettyio.gim.server.GimContext;

/**
 * 单聊处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see SingleChatHandler
 * @since
 */
public class SingleChatHandler extends AbsChatHandler<SingleChatReqClass.SingleChatReq> {

    private GimContext gimContext;

    public SingleChatHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<SingleChatReqClass.SingleChatReq> bodyClass() {
        return SingleChatReqClass.SingleChatReq.class;
    }

    @Override
    public void handler(MessageClass.Message message, SingleChatReqClass.SingleChatReq bsBody, AioChannel aioChannel) throws Exception {
        // 接收者的ID
        String receiverId = bsBody.getReceiverId();
        gimContext.messagEmitter.sendToUser(receiverId, message);
    }

}
