package com.gettyio.gim.client.core;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.gim.client.expansion.HeartBeatHandler;
import com.gettyio.gim.client.packet.MessageClass;

/**
 * 消息处理类
 *
 * @author gogym
 * @version 1.0
 * @date 2016-9-30
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<MessageClass.Message> {

    InternalLogger logger = InternalLoggerFactory.getInstance(ChatClientHandler.class);
    GimContext gimContext;

    public ChatClientHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    @Override
    public void channelAdded(AioChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + "Server connection successful.Server address:" + aioChannel.getRemoteAddress().getHostString());
        gimContext.aioChannel = aioChannel;
        gimContext.channelStatusListener.channelAdd(gimContext, aioChannel.getChannelId());

        if (gimContext.gimConfig.isEnableHeartBeat()) {
            //是否开启了心跳
            HeartBeatHandler heartBeatHandler = new HeartBeatHandler(gimContext);
            heartBeatHandler.start();
        }

    }

    @Override
    public void channelClosed(AioChannel aioChannel) throws Exception {
        logger.info(aioChannel.getChannelId() + "Server disconnected");
        gimContext.channelStatusListener.channelClose(aioChannel.getChannelId());
        if (gimContext.gimConfig.isEnableHeartBeat()) {
            HeartBeatHandler heartBeatHandler = new HeartBeatHandler(gimContext);
            heartBeatHandler.stop();
        }
    }


    @Override
    public void channelRead0(AioChannel aioChannel, MessageClass.Message message) throws Exception {
        // 消息会在这个方法接收到，msg就是经过解码器解码后得到的消息，框架自动帮你做好了粘包拆包和解码的工作
        //Message will be received in this method, MSG is decoded , the framework automatically help you do the work of unpacking and decoding
        gimContext.chatListener.read(message, aioChannel);
    }


    @Override
    public void exceptionCaught(AioChannel aioChannel, Throwable cause) throws Exception {
        logger.error(aioChannel.getChannelId() + "Exception, closed.", cause);
        aioChannel.close();
        gimContext.channelStatusListener.channelClose(aioChannel.getChannelId());
    }
}
