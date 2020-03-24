package com.gettyio.gim.client.core;/*
 * 类名：GimClient
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/2/17
 */

import com.gettyio.core.channel.config.AioClientConfig;
import com.gettyio.core.channel.starter.AioClientStarter;
import com.gettyio.gim.client.expansion.DelayMsgQueueListener;
import com.gettyio.gim.client.listener.ChannelReadListener;
import com.gettyio.gim.client.listener.ChannelStatusListener;


public class GimClient {

    //总的配置类
    private GimConfig gimConfig;
    private GimContext gimContext;
    private AioClientStarter aioClientStarter;

    private Thread gimThread;


    public GimClient(GimConfig gimConfig, ChannelStatusListener gimListener) {
        this.gimConfig = gimConfig;
        gimContext = new GimContext(gimConfig, gimListener);
    }


    public GimClient channelReadListener(ChannelReadListener channelReadListener) {
        gimContext.channelReadListener(channelReadListener);
        return this;
    }

    /**
     * Description: start
     *
     * @see
     */
    public void start() throws Exception {
        // 检查配置
        checkConfig();
        // 启动
        start0();
    }


    public void shutDown() {

        if (aioClientStarter != null) {
            aioClientStarter.shutdown();
        }
    }

    private void checkConfig() throws Exception {

        // 启动前，做系统自查，检查集群，离线等配置等
        if (gimConfig == null) {
            throw new Exception("[GimConfig can't be null]");
        }

        // 检查端口号
        if (gimConfig.getHost() == null) {
            throw new Exception("[the host can't be null]");
        }

        // 检查端口号
        if (gimConfig.getPort() == null) {
            throw new Exception("[the port can't be null]");
        }

        if (gimContext.channelReadListener == null) {
            throw new Exception("[the channelReadListener can't be null]");
        }

    }

    /**
     * Description: 处理延迟队列
     *
     * @see
     */
    private void startDelayQueueRunable() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    DelayMsgQueueListener.takeMessage(gimContext);
                }
            }
        }.start();
    }


    /**
     * 内部启动
     *
     * @return void
     * @params []
     */
    private void start0() {
        //初始化配置对象
        AioClientConfig aioClientConfig = new AioClientConfig();
        aioClientConfig.setHost(gimConfig.getHost());
        aioClientConfig.setPort(gimConfig.getPort());
        aioClientStarter = new AioClientStarter(aioClientConfig).channelInitializer(new GimClientInitializer(gimContext));
        //启动服务
        gimThread = new Thread(() -> {
            try {
                aioClientStarter.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gimThread.start();

        //如果开启了重发
        if (gimConfig.isAutoRewrite()) {
            startDelayQueueRunable();
        }
    }


}
