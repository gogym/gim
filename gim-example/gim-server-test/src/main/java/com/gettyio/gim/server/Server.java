package com.gettyio.gim.server;

import com.gettyio.gim.listener.ChannelBindListener;
import com.gettyio.gim.listener.ChannelReadListener;
import com.gettyio.gim.listener.ChannelStatusListener;
import com.gettyio.gim.listener.OfflineMsgListener;
import com.gettyio.gim.starter.GimStarter;
import com.gettyio.gim.starter.GimStarterImpl;
import com.gettyio.gim.starter.OnStartListener;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName Server.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/19/ 17:26:00
 */
public class Server {

    public static void main(String[] args) {

        //实例化gim配置
        GimConfig gimConfig = new GimConfig();
        gimConfig.addHost(new GimHost(4567)).addHost(new GimHost(4568)).autoRewrite(true);

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
