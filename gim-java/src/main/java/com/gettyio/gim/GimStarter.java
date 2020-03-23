package com.gettyio.gim;
/*
 * 类名：GimStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/19
 */


import com.gettyio.core.channel.config.AioServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;
import com.gettyio.gim.cluster.ClusterMsgListener;
import com.gettyio.gim.queue.DelayMsgQueueListener;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.server.GimServerInitializer;

public class GimStarter {

    //总的配置类
    private GimConfig gimConfig;
    //上下文
    private GimContext gimContext;

    private AioServerStarter server;


    public GimStarter(GimConfig gimConfig) {
        this.gimConfig = gimConfig;
        gimContext = new GimContext(gimConfig);
    }

    /**
     * Description: start
     *
     * @throws Exception
     * @see
     */
    public void start(OnStartListener onStartListener) throws Exception {
        // 检查配置
        checkConfig();
        start0();
        onStartListener.onStart(gimContext);
    }

    public void shutDown() {
        if (server != null) {
            server.shutdown();
        }
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
    private void startDelayQueueRunable() {
        new Thread(new DelayMsgQueueListener(gimContext)).start();
    }

    private void startClusterRunable() {
        new Thread(new ClusterMsgListener(gimContext)).start();
    }


    private void start0() {
        //初始化配置对象
        AioServerConfig aioServerConfig = new AioServerConfig();
        //设置host,不设置默认localhost
        aioServerConfig.setHost(gimConfig.getHost());
        //设置端口号
        aioServerConfig.setPort(gimConfig.getPort());
        aioServerConfig.setServerChunkSize(gimConfig.getServerChunkSize());
        server = new AioServerStarter(aioServerConfig);
        server.channelInitializer(new GimServerInitializer(gimContext));
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果开启了重发
        if (gimConfig.isAutoRewrite()) {
            startDelayQueueRunable();
        }

        //是否开启了集群
        if (gimConfig.isEnableCluster()) {
            startClusterRunable();
        }

    }


    public interface OnStartListener {
        void onStart(GimContext gimContext);
    }

}
