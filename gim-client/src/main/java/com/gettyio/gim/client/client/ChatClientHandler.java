package com.gettyio.gim.client.client;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.gim.client.packet.MessageClass;

/**
 * netty处理类
 *
 * @author kokJuis
 * @version 1.0
 * @date 2016-9-30
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<MessageClass.Message> {

    // 把消息传给监听
    GimContext gimContext;

    public ChatClientHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public void channelAdded(AioChannel aioChannel) throws Exception {
        gimContext.gimListener.channelAdd(aioChannel.getRemoteAddress().toString());
        gimContext.aioChannel = aioChannel;
        System.out.println("[Client] - " + aioChannel.getRemoteAddress() + " 连接过来");
    }

    @Override
    public void channelClosed(AioChannel aioChannel) throws Exception {
        gimContext.gimListener.channelClose(aioChannel.getRemoteAddress().toString());
        System.out.println("[Client] - " + aioChannel.getRemoteAddress() + " 离开");

    }


    @Override
    public void channelRead0(AioChannel aioChannel, MessageClass.Message message) throws Exception {
        // 消息会在这个方法接收到，msg就是经过解码器解码后得到的消息，框架自动帮你做好了粘包拆包和解码的工作
        gimContext.chatListener.read(message, aioChannel);
        gimContext.gimListener.channelRead(aioChannel.getRemoteAddress().toString(), message);
    }


    @Override
    public void exceptionCaught(AioChannel aioChannel, Throwable cause) throws Exception {
        aioChannel.close();
        gimContext.gimListener.channelClose(aioChannel.getRemoteAddress().toString());
        // 当出现异常就关闭连接
        System.out.println("Client:" + aioChannel.getRemoteAddress() + "异常,已被服务器关闭");
        cause.printStackTrace();
    }
}
