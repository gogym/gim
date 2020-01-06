package com.gettyio.gim.client;/*
 * 类名：ClientStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/25
 */

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.client.GimConfig;
import com.gettyio.gim.client.listener.GimListener;
import com.gettyio.gim.client.message.MessageGenerate;
import com.gettyio.gim.client.packet.MessageClass;

public class ClientStarter {

    public static void main(String[] args) {


        GimConfig gimConfig = new GimConfig();
        gimConfig.host("127.0.0.1");
        gimConfig.port(4567);

        GimClientStarter gimClientStarter = new GimClientStarter(gimConfig, new GimListener() {
            @Override
            public void channelRead(String address, MessageClass.Message message) {

            }

            @Override
            public void channelWrite(boolean isSuccess, MessageClass.Message message) {

            }

            @Override
            public void channelAdd(String address) {

            }

            @Override
            public void channelClose(String address) {

            }
        });

        try {
            gimClientStarter.start();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        MessageClass.Message m = MessageGenerate.crateConnectReq("123");
        gimClientStarter.getGimContext().aioChannel.writeAndFlush(m);

    }
}
