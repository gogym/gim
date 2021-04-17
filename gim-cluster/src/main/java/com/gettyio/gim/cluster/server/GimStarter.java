package com.gettyio.gim.cluster.server;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName GimStarter.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/09/ 17:21:00
 */
public interface GimStarter {


    /**
     * 启动服务
     */
    void start();

    /**
     * 停止服务
     */
    void shutDown();
}
