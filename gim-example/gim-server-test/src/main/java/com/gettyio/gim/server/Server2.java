package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.listener.*;
import com.gettyio.gim.starter.GimStarter;
import com.gettyio.gim.starter.GimStarterImpl;
import com.gettyio.gim.redis.RedisProperties;
import com.gettyio.gim.starter.OnStartListener;

import java.util.ArrayList;
import java.util.List;

public class Server2 {

    public static void main(String[] args) {
        Server2 server2=new Server2();
        server2.openServerA();
        server2.openServerB();
    }



    private void openServerA(){
        //集群的redis配置
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("127.0.0.1");
        redisProperties.setPort(6379);
        //redisProperties.setPassword("inhand@redis2017");

        //实例化gim配置
        GimConfig gimConfig = new GimConfig();
        gimConfig.addHost(new GimHost(4567)).autoRewrite(true);
        gimConfig.cluster(true,"a",redisProperties);

        //实例化启动器
        GimStarter gimStarter = new GimStarterImpl(gimConfig, new OnStartListener() {
            @Override
            public void onStart(GimContext gimContext) {
                //启动成功返回上下文
                gimContext.setChannelStatusListener(new ChannelStatusListener() {
                    @Override
                    public void channelAdd(GimContext gimContext, String channelId) {
                        System.out.println(channelId+" is add");
                    }
                    @Override
                    public void channelClose(String channelId) {
                        System.out.println(channelId+" is close");
                    }
                });

                gimContext.setChannelBindListener(new ChannelBindListener() {
                    @Override
                    public void onBind(String message) {
                        System.out.println("绑定成功");
                    }

                    @Override
                    public void onUnbind(String message) {
                        System.out.println("解绑成功");
                    }
                });

                gimContext.setChannelReadListener(new ChannelReadListener() {
                    @Override
                    public void channelRead(String message) {
                        System.out.println("来自客户端消息："+message);
                    }
                });

                gimContext.setOfflineMsgListener(new OfflineMsgListener() {
                    @Override
                    public void onMsg(String msg) {
                        System.out.println("离线客户端消息："+msg);
                    }
                });



                List<String> groupItems=new ArrayList<>();
                groupItems.add("378797446421155840");
                groupItems.add("390291164848328704");
                try {
                    gimContext.getGimBind().bindGroup("1",groupItems);
                    System.out.println("绑定群成员成功");
                } catch (Exception e) {
                    System.out.println("绑定群成员失败");
                    e.printStackTrace();

                }


            }
        });
        gimStarter.start();
    }


    private void openServerB(){
        //集群的redis配置
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("127.0.0.1");
        redisProperties.setPort(6379);
        //redisProperties.setPassword("inhand@redis2017");

        //实例化gim配置
        GimConfig gimConfig = new GimConfig();
        gimConfig.addHost(new GimHost(4568)).autoRewrite(true);
        gimConfig.cluster(true,"b",redisProperties);

        //实例化启动器
        GimStarter gimStarter = new GimStarterImpl(gimConfig, new OnStartListener() {
            @Override
            public void onStart(GimContext gimContext) {
                //启动成功返回上下文
                gimContext.setChannelStatusListener(new ChannelStatusListener() {
                    @Override
                    public void channelAdd(GimContext gimContext, String channelId) {
                        System.out.println(channelId+" is add");
                    }

                    @Override
                    public void channelClose(String channelId) {
                        System.out.println(channelId+" is close");
                    }
                });

                gimContext.setChannelBindListener(new ChannelBindListener() {
                    @Override
                    public void onBind(String message) {
                        System.out.println("绑定成功");
                    }

                    @Override
                    public void onUnbind(String message) {
                        System.out.println("解绑成功");
                    }
                });

                gimContext.setChannelReadListener(new ChannelReadListener() {
                    @Override
                    public void channelRead(String message) {
                        System.out.println("来自客户端消息："+message);
                    }
                });

                gimContext.setOfflineMsgListener(new OfflineMsgListener() {
                    @Override
                    public void onMsg(String msg) {
                        System.out.println("离线客户端消息："+msg);
                    }
                });

            }
        });
        gimStarter.start();
    }

}
