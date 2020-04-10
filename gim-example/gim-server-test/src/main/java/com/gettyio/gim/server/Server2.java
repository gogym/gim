package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.GimStarter;
import com.gettyio.gim.redis.RedisProperties;
import com.gettyio.gim.listener.OfflineMsgListener;

public class Server2 {


    public static void main(String[] args) {

        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("192.168.167.111");
        redisProperties.setPort(6379);
        redisProperties.setPassword("inhand@redis2017");


        GimConfig gimConfig = new GimConfig();
        //端口号不同，用于测试集群
        gimConfig.port(4568)
                .enableHeartBeat(true)
                .enableOffline(true)
                .cluster(true, "two", redisProperties);

        GimStarter gimStarter = new GimStarter(gimConfig);

        try {
            gimStarter.start(new GimStarter.OnStartListener() {
                @Override
                public void onStart(GimContext gimContext) {
                    gimContext.offlineMsgListener(new OfflineMsgListener() {
                        @Override
                        public void onMsg(String msg) {
                            System.out.println("来了个离线消息:" + msg);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
