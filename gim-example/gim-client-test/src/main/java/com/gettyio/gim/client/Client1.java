package com.gettyio.gim.client;/*
 * 类名：ClientStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/25
 */


import com.gettyio.gim.client.core.GimClient;
import com.gettyio.gim.client.core.GimConfig;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.listener.ChannelReadListener;
import com.gettyio.gim.client.listener.ChannelStatusListener;

import java.util.Scanner;

public class Client1 {

    private static String SOCKET_HOST = "localhost";
    private static int SOCKET_PORT = 4567;


    private static String senderId="123";
    private static String receiverId="456";

    public static void main(String[] args) {

        GimConfig gimConfig = new GimConfig()
                .host(SOCKET_HOST)
                .port(SOCKET_PORT)
                .enableHeartBeat(true)
                .heartBeatInterval(5000L)
                .enableReConnect(true).autoRewrite(true);

        GimClient gimClient = new GimClient(gimConfig, new ChannelStatusListener() {
            @Override
            public void channelAdd(final GimContext gimContext, String address) {
                System.out.println("连接服务器成功");
                gimContext.gimBind.bindUser(senderId);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("请输入需要发送的消息：");
                        Scanner sc = new Scanner(System.in);
                        while (sc.hasNext()) {
                            String s = sc.nextLine();
                            if (!s.equals("")) {
                                //gimContext.messagEmitter.sendSingleChatText(senderId, receiverId, s);
                                gimContext.messagEmitter.sendGroupChatText(senderId, "1", s,null);
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void channelClose(String address) {

            }
        }).channelReadListener(new ChannelReadListener() {

            @Override
            public void channelRead(String message) {
                System.out.println("接收的消息:\n" + message);
            }
        });

        try {
            gimClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
