package com.gettyio.gim.server;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.gim.packet.MessageClass;

/**
 * 处理类
 *
 * @author kokJuis
 * @version 1.0
 * @date 2016-9-30
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<MessageClass.Message> {

    InternalLogger logger = InternalLoggerFactory.getInstance(ChatServerHandler.class);

    // 把消息传给监听
    GimContext gimContext;

    public ChatServerHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public void channelAdded(AioChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + " connection successful.address:" + aioChannel.getRemoteAddress().getHostString());
        gimContext.channels.add(aioChannel);
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelAdd(gimContext, aioChannel.getChannelId());
        }
    }

    @Override
    public void channelClosed(AioChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + " disconnected");
        gimContext.gimBind.unbindUserByChannelId(aioChannel.getChannelId());
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelClose(aioChannel.getChannelId());
        }

    }


    @Override
    public void channelRead0(AioChannel aioChannel, MessageClass.Message message) throws Exception {
        // 消息会在这个方法接收到，msg就是经过解码器解码后得到的消息，框架自动帮你做好了粘包拆包和解码的工作
        gimContext.chatListener.read(message, aioChannel);
    }


    @Override
    public void exceptionCaught(AioChannel aioChannel, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        logger.error(aioChannel.getChannelId() + " Exception, closed.", cause);
        aioChannel.close();
        if (gimContext.channelStatusListener != null) {
            gimContext.channelStatusListener.channelClose(aioChannel.getChannelId());
        }
    }
}
