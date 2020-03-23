
package com.gettyio.gim.client.handler;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.comm.Type;
import com.gettyio.gim.client.listener.ChatListener;
import com.gettyio.gim.client.message.MessageGenerate;
import com.gettyio.gim.client.packet.MessageClass;

import java.util.Map;

/**
 * 〈业务处理器基类〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see BaseChatHandler
 * @since
 */
public class BaseChatHandler implements ChatListener {

    Map<Integer, AbsChatHandler<?>> handlerMap;

    public BaseChatHandler(Map<Integer, AbsChatHandler<?>> handlerMap) {
        this.handlerMap = handlerMap;
    }


    @Override
    public void read(MessageClass.Message message, AioChannel aioChannel) throws Exception {

        //自动返回ack给服务器端
        if (message.getReqType() != Type.ACK_REQ) {
            MessageClass.Message ack = MessageGenerate.createAck(message.getId());
            aioChannel.writeAndFlush(ack);
        }
        //根据消息查找对应的处理器
        Integer type = message.getReqType();
        AbsChatHandler<?> absChatHandler = handlerMap.get(type);
        if (absChatHandler == null) {
            throw new Exception("找不到对应消息处理器");
        }

        absChatHandler.handler(message, aioChannel);
    }
}
