package com.gettyio.gim.cluster.config;

import com.gettyio.gim.cluster.server.GimHost;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName ClusterConfig.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/22/ 20:56:00
 */
public class ClusterConfig {

    /**
     * 连接地址列表
     */
    private final List<GimHost> hostList = new ArrayList<>();

    /**
     * 默认不开启集群
     */
    private final boolean enableCluster;

    /**
     * 服务器唯一标示
     */
    private final String serverId;

    /**
     * 集群方式
     */
    private final Integer clusterType;


    /**
     * 构造方法
     * @param enableCluster
     * @param serverId
     * @param clusterType
     */
    public ClusterConfig(boolean enableCluster, String serverId, Integer clusterType) {
        this.enableCluster = enableCluster;
        this.serverId = serverId;
        this.clusterType = clusterType;
    }

    public boolean isEnableCluster() {
        return enableCluster;
    }

    public String getServerId() {
        return serverId;
    }

    public Integer getClusterType() {
        return clusterType;
    }

    public List<GimHost> getHosts() {
        return hostList;
    }
}
