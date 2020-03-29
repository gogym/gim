package com.gettyio.gim.client.core;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.handler.codec.protobuf.ProtobufDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufEncoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import com.gettyio.core.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import com.gettyio.core.handler.timeout.ReConnectHandler;
import com.gettyio.core.logging.InternalLogger;
import com.gettyio.core.logging.InternalLoggerFactory;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.DefaultChannelPipeline;
import com.gettyio.gim.client.packet.MessageClass;


/**
 * netty处理器配置
 *
 * @author kokJuis
 * @version 1.0
 * @date 2016-9-30
 */
public class GimClientInitializer extends ChannelInitializer {

    InternalLogger logger = InternalLoggerFactory.getInstance(GimClientInitializer.class);
    GimContext gimContext;

    public GimClientInitializer(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        //获取责任链对象
        DefaultChannelPipeline pipeline = channel.getDefaultChannelPipeline();

        // ----配置Protobuf处理器----
        // 用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 配置Protobuf解码处理器，消息接收到了就会自动解码，ProtobufDecoder是netty自带的，Message是自己定义的Protobuf类
        pipeline.addLast(new ProtobufDecoder(MessageClass.Message.getDefaultInstance()));
        // 用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // 配置Protobuf编码器，发送的消息会先经过编码
        pipeline.addLast(new ProtobufEncoder());
        // ----Protobuf处理器END----

        if (gimContext.gimConfig.isEnableReConnect()) {
            //是否开启断线重连
            pipeline.addLast(new ReConnectHandler(channel));
        }

        pipeline.addLast(new ChatClientHandler(gimContext));

    }
}
