package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.GimStarter;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.redis.RedisProperties;

import java.util.ArrayList;
import java.util.List;

public class Server3 {


    public static void main(String[] args) {

        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("192.168.167.111");
        redisProperties.setPort(6379);
        redisProperties.setPassword("inhand@redis2017");

        GimHost gimHost = new GimHost(4567,SocketType.WEB_SOCKET);
        GimHost gimHost2 = new GimHost(4568,SocketType.SOCKET);
        List<GimHost> list = new ArrayList<>();
        list.add(gimHost);
        list.add(gimHost2);

        GimConfig gimConfig = new GimConfig();
        gimConfig.hosts(list);

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
