package com.gettyio.gim.client;/*
 * 类名：ClientStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/25
 */


import com.gettyio.gim.client.client.GimClient;
import com.gettyio.gim.client.client.GimConfig;
import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.client.listener.ChannelBindListener;
import com.gettyio.gim.client.listener.ChannelReadListener;
import com.gettyio.gim.client.listener.ChannelStatusListener;
import com.gettyio.gim.comm.ClientAuth;

import java.util.Scanner;

public class Client1 {

    private static String SOCKET_HOST = "localhost";
    private static int SOCKET_PORT = 4567;


    private static String senderId = "123";
    private static String senderName = "小明";
    private static String senderHeadImg = "";

    private static String receiverId = "456";
    private static String receiverName = "小方";
    private static String receiverHeadImg = "";


    private static String groupId = "1";
    private static String groupName = "群聊";
    private static String groupHeadImg = "";


    public static void main(String[] args) {

        //获取证书
        String pkPath = Client1.class.getClassLoader().getResource("clientStore.jks").getPath();

        GimConfig gimConfig = new GimConfig()
                .host(SOCKET_HOST)
                .port(SOCKET_PORT)
                .enableHeartBeat(true)
                .heartBeatInterval(5000)
                .enableReConnect(true).autoRewrite(true);
                //.openSsl(pkPath, "123456", "123456", ClientAuth.REQUIRE);

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
                                //gimContext.messagEmitter.sendSingleChatText(senderId, senderName, senderHeadImg, receiverId, receiverName, receiverHeadImg, s);
                                gimContext.messagEmitter.sendGroupChatText(senderId, senderName, senderHeadImg, groupId, groupName, groupHeadImg, s, null);
                                //解绑用户
                                //gimContext.gimBind.unbindUser(senderId);
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
            public void onRead(String message) {
                System.out.println("接收的消息:\n" + message);
            }
        }).channelBindListener(new ChannelBindListener() {
            @Override
            public void onBind(String message) {
                System.out.println("绑定用户成功");
            }

            @Override
            public void onUnbind(String message) {
                System.out.println("解绑用户成功");
            }
        });

        try {
            gimClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
