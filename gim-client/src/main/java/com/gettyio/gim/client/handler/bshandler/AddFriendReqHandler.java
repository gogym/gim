package com.gettyio.gim.client.handler.bshandler;/*
 * 类名：AddFriendReq
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/2/24
 */

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.packet.MessageClass;
import com.google.protobuf.util.JsonFormat;

public class AddFriendReqHandler extends AbsChatHandler<MessageClass.Message> {


    private GimContext gimContext;

    public AddFriendReqHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message, AioChannel aioChannel) throws Exception {
        String msgJson = JsonFormat.printer().print(message);
        gimContext.channelReadListener.channelRead(msgJson);
    }
}
