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
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

/**
 * 单聊处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see SingleChatHandler
 * @since
 */
public class SingleChatHandler extends AbsChatHandler<MessageClass.Message> {

    private GimContext gimContext;

    public SingleChatHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message,  SocketChannel socketChannel) throws Exception {
        // 接收者的ID
        String receiverId = message.getReceiverId();
        gimContext.messagEmitter.sendToUser(receiverId, message);

        if (gimContext.channelReadListener != null) {
            String msgJson = JsonFormat.printer().print(message);
            gimContext.channelReadListener.channelRead(msgJson);
        }
    }

}
