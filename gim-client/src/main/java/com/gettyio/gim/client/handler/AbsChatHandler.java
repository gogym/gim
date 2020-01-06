package com.gettyio.gim.client.handler;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.intf.ChatHandlerIntf;
import com.gettyio.gim.client.packet.MessageClass;
import com.google.protobuf.GeneratedMessageV3;

/**
 * 〈消息分发基类〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see AbsChatHandler
 * @since
 */
public abstract class AbsChatHandler<T extends GeneratedMessageV3> implements ChatHandlerIntf {


    // 获取泛型类，从子类中返回，这个很重要
    public abstract Class<T> bodyClass();

    @Override
    public void handler(MessageClass.Message message, AioChannel ctx) throws Exception {
        // 根据类型转换消息
        T bsBody = message.getBody().unpack(bodyClass());
        handler(message, bsBody, ctx);
    }

    // 把消息分发给指定的业务处理器
    public abstract void handler(MessageClass.Message message, T bsBody, AioChannel aioChannel) throws Exception;

}
