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
import com.gettyio.gim.client.core.OnConnectLintener;
import com.gettyio.gim.client.listener.*;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;

import java.util.Scanner;

public class Client2 {

    private static String SOCKET_HOST = "localhost";
    private static int SOCKET_PORT = 4568;

    private static String senderId = "390291164848328704";
    private static String senderName = "小红";

    private static String receiverId = "378797446421155840";
    private static String receiverName = "小明";

    private static String groupId = "1";
    private static String groupName = "群聊";


    public static void main(String[] args) {

        //获取证书
        String pkPath = Client1.class.getClassLoader().getResource("clientStore.jks").getPath();

        GimConfig gimConfig = new GimConfig()
                .host(SOCKET_HOST)
                .port(SOCKET_PORT)
                .enableHeartBeat(false, 5000)
                .enableReConnect(true).autoRewrite(true);
        //.openSsl(pkPath, "123456", "123456", ClientAuth.REQUIRE);

        GimClient gimClient = new GimClient(gimConfig);

        try {
            gimClient.start(new OnConnectLintener() {
                @Override
                public void onCompleted(final GimContext gimContext) {
                    System.out.println("连接服务器成功");
                    gimContext.getGimBind().bind(senderId, new ChannelBindListener() {
                        @Override
                        public void onBind(MessageClass.Message message) {
                            System.out.println("绑定用户成功");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("请输入需要发送的消息：");
                                    Scanner sc = new Scanner(System.in);
                                    while (sc.hasNext()) {
                                        String s = sc.nextLine();
                                        if (!s.equals("")) {
                                            //发送消息
                                            MessageClass.Message message = MessageGenerate.getInstance().createSingleMsgReq(senderId, receiverId, s);
                                            gimContext.getMessageEmitter().send(message);
                                        }
                                    }
                                }
                            }).start();
                        }
                    });


                    gimContext.setChannelReadListener(new ChannelReadListener() {
                        @Override
                        public void onRead(MessageClass.Message message) {
                            System.out.println("接收的消息:\n" + message.toString());
                        }
                    });

                    gimContext.setChannelReSendListener(new ChannelReSendListener() {
                        @Override
                        public void onSuccess(MessageClass.Message msg) {
                            System.out.println("重发消息成功:\n" + msg);
                        }

                        @Override
                        public void onFail(MessageClass.Message msg) {
                            System.out.println("重发消息失败:\n" + msg);
                        }
                    });

                    gimContext.setChannelAckListener(new ChannelAckListener() {
                        @Override
                        public void onAck(String ack) {
                            System.out.println("接收到服务器ack:\n" + ack);
                        }
                    });

                }

                @Override
                public void onFailed(Throwable throwable) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

