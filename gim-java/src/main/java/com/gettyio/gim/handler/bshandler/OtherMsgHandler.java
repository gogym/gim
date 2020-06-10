package com.gettyio.gim.handler.bshandler;/*
 * 类名：OtherMsgHandler
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/5/25
 */

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

public class OtherMsgHandler extends AbsChatHandler<MessageClass.Message> {

    private GimContext gimContext;

    public OtherMsgHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message, SocketChannel socketChannel) throws Exception {

        //自动返回ack给客户端
        if (message.getReqType() != Type.ACK_REQ && message.getReqType() != Type.HEART_BEAT_REQ) {
            if (null != socketChannel) {
                //非集群消息socketChannel不为空
                MessageClass.Message ack = MessageGenerate.getInstance().createAck(message.getId());
                socketChannel.writeAndFlush(ack);
            } else {
                //集群过来的消息ack已经提前处理。无需在此处理
            }
        }
        // 接收者的ID
        String toId = message.getToId();
        gimContext.messagEmitter.send(toId, message);

        if (gimContext.channelReadListener != null) {
            String msgJson = JsonFormat.printer().print(message);
            gimContext.channelReadListener.channelRead(msgJson);
        }
    }
}
