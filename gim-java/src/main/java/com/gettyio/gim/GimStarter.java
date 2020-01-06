package com.gettyio.gim;/*
 * 类名：GimStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/19
 */


import com.gettyio.core.channel.config.AioServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;
import com.gettyio.gim.listener.GimListener;
import com.gettyio.gim.listener.queue.DelayMsgQueueListener;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.server.GimServerInitializer;

import java.net.StandardSocketOptions;

public class GimStarter {

    //总的配置类
    private GimConfig gimConfig;
    private GimContext gimContext;


    public GimStarter(GimConfig gimConfig, GimListener gimListener) {
        this.gimConfig = gimConfig;
        gimContext = new GimContext(gimConfig, gimListener);
    }

    public GimContext getGimContext() {
        return gimContext;
    }

    /**
     * Description: start
     *
     * @throws Exception
     * @see
     */
    public void start() throws Exception {
        // 检查配置
        checkConfig();
        // 启动
        //startQueueListener();
        start0();
    }

    private void checkConfig() throws Exception {

        // 启动前，做系统自查，检查集群，离线等配置等
        if (gimConfig == null) {
            throw new Exception("[GimConfig can't be not null]");
        }

        // 检查端口号
        if (gimConfig.getPort() == null) {
            throw new Exception("[the port can't be not null]");
        }

    }

    /**
     * Description: 处理延迟队列监听
     *
     * @see
     */
    private void startQueueListener() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    DelayMsgQueueListener.takeMessage(gimContext);
                }
            }
        }.start();
    }


    private void start0() {
        //初始化配置对象
        AioServerConfig aioServerConfig = new AioServerConfig();
        //设置host,不设置默认localhost
        aioServerConfig.setHost(gimConfig.getHost());
        //设置端口号
        aioServerConfig.setPort(gimConfig.getPort());
        //设置服务器端内存池最大可分配空间大小，默认256mb，内存池空间可以根据吞吐量设置。
        // 尽量可以设置大一点，因为这不会真正的占用系统内存，只有真正使用时才会分配
        aioServerConfig.setServerChunkSize(512 * 1024 * 1024);
        //设置数据输出器队列大小，一般不用设置这个参数，默认是10*1024*1024
        aioServerConfig.setBufferWriterQueueSize(10 * 1024 * 1024);
        //设置读取缓存块大小，一般不用设置这个参数，默认2048字节
        aioServerConfig.setReadBufferSize(2048);
        //设置内存池等待分配内存的最大阻塞时间，默认是1秒
        aioServerConfig.setChunkPoolBlockTime(1000);
        //设置SocketOptions
        aioServerConfig.setOption(StandardSocketOptions.SO_RCVBUF, 8192);
        AioServerStarter server = new AioServerStarter(aioServerConfig);
        server.channelInitializer(new GimServerInitializer(gimContext));

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
