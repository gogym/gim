package com.gettyio.gim.server;/*
 * 类名：server
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/24
 */

import com.gettyio.gim.GimStarter;
import com.gettyio.gim.comm.ClientAuth;
import com.gettyio.gim.redis.RedisProperties;
import com.gettyio.gim.listener.OfflineMsgListener;

public class Server1 {


    public static void main(String[] args) {

        //集群redis配置，如果不需要集群，可以不配置
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("192.168.167.111");
        redisProperties.setPort(6379);
        redisProperties.setPassword("inhand@redis2017");


        //获取证书
        String pkPath = Server1.class.getClassLoader().getResource("serverStore.jks").getPath();

        //gim配置
        GimConfig gimConfig = new GimConfig();
        gimConfig.serverChunkSize(1024 * 1024 * 100);
        gimConfig.port(4569)//端口号
                .enableHeartBeat(false)//是否开启心跳检测
                .heartBeatInterval(60)
                .enableOffline(false)//是否开启离线监听
                .cluster(false, "one", redisProperties)//是否开启集群
                .autoRewrite(true).reWriteNum(3).reWriteDelay(5000L);
        //.openSsl(pkPath,"123456","123456", ClientAuth.REQUIRE);
        //实例化gim
        GimStarter gimStarter = new GimStarter(gimConfig);

        try {
            //启动服务
            gimStarter.start(new GimStarter.OnStartListener() {
                @Override
                public void onStart(GimContext gimContext) {

                    try {
                        gimContext.gimBind.bindGroup("1", "123");
                        gimContext.gimBind.bindGroup("1", "456");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
