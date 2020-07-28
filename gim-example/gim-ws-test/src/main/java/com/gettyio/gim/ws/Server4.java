package com.gettyio.gim.ws;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */


import com.gettyio.gim.GimStarter;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.redis.RedisProperties;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;

public class Server4 {


    public static void main(String[] args) {

        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("192.168.167.111");
        redisProperties.setPort(6379);
        redisProperties.setPassword("inhand@redis2017");


        GimConfig gimConfig = new GimConfig();
        gimConfig.port(4567);

        gimConfig.enableOffline(true);
        gimConfig.cluster(false, "one", redisProperties);
        gimConfig.enableHeartBeat(false);

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
