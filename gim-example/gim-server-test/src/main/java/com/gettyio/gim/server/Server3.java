package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.GimStarter;
import com.gettyio.gim.listener.ChannelAckListener;
import com.gettyio.gim.listener.ChannelReadListener;
import com.gettyio.gim.listener.ChannelStatusListener;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.redis.RedisProperties;

import java.util.ArrayList;
import java.util.List;

public class Server3 {


    public static void main(String[] args) {

        //集群的redis配置
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("192.168.167.111");
        redisProperties.setPort(6379);
        redisProperties.setPassword("inhand@redis2017");

        //同时开启websocet和普通socket支持。这里支持端口多开
        GimHost gimHost = new GimHost(4567, SocketType.WEB_SOCKET /*注意，这里要指定 SocketType.WEB_SOCKET 不指定默认是普通socket*/);
        GimHost gimHost2 = new GimHost(4568, SocketType.SOCKET);
        List<GimHost> list = new ArrayList<>();
        list.add(gimHost);
        list.add(gimHost2);

        //实例化gim配置
        GimConfig gimConfig = new GimConfig();
        //添加开启端口列表
        gimConfig.hosts(list);

        //开启离线
        gimConfig.enableOffline(true);
        //集群
        gimConfig.cluster(false, "one", redisProperties);
        //心跳
        gimConfig.enableHeartBeat(false);
        //实例化启动器
        GimStarter gimStarter = new GimStarter(gimConfig);

        try {
            //启动，并且监听启动状态
            gimStarter.start(new GimStarter.OnStartListener() {
                @Override
                public void onStart(GimContext gimContext) {

                    //启动成功，可以在这里添加想要的监听

                    //离线监听
                    gimContext.offlineMsgListener(new OfflineMsgListener() {
                        @Override
                        public void onMsg(String msg) {
                            System.out.println("来了个离线消息:" + msg);
                        }
                    });

                    //消息监听
                    gimContext.channelReadListener(new ChannelReadListener() {
                        @Override
                        public void channelRead(String message) {

                        }
                    });

                    //连接监听
                    gimContext.channelStatusListener(new ChannelStatusListener() {
                        @Override
                        public void channelAdd(GimContext gimContext, String address) {

                        }

                        @Override
                        public void channelClose(String channelId) {

                        }
                    });

                    //ack监听
                    gimContext.channelAckListener(new ChannelAckListener() {
                        @Override
                        public void onAck(String ack) {

                        }
                    });



                    try {
                        //绑定群组信息
                        List<String> ids=new ArrayList<>();
                        //群内用户标记
                        ids.add("1");
                        ids.add("2");
                        //绑定到群
                        gimContext.gimBind.bindGroup("gid",ids);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
