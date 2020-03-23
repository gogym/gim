package com.gettyio.gim.handler.bshandler;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.packet.MessageClass;

public class HeartBeatHandler extends AbsChatHandler<MessageClass.Message> {

    @Override
    public Class<MessageClass.Message> bodyClass() {
        return MessageClass.Message.class;
    }

    @Override
    public void handler(MessageClass.Message message, AioChannel aioChannel) {
        //客户端发来的心跳包，一般不需要特殊处理
        System.out.println("心跳包");
    }

}
