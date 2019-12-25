package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.GimStarter;
import com.gettyio.gim.listener.GimListener;
import com.gettyio.gim.packet.MessageClass;

public class ServerStarter {


    public static void main(String[] args) {

        GimConfig gimConfig = new GimConfig();
        gimConfig.port(4567);

        GimStarter gimStarter = new GimStarter(gimConfig, new GimListener() {
            @Override
            public void bindUser(String userId, String address) {

            }

            @Override
            public void unbindUser(String userId) {

            }

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
            gimStarter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
