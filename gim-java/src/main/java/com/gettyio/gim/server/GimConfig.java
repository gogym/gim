/*
 * 文件名：GimConfig.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.server;


import com.gettyio.gim.cluster.redis.RedisProperties;
import com.gettyio.gim.common.Const;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 〈配置类〉 〈功能详细描述〉
 *
 * @author gogym
 * @version 2019年7月12日
 * @see GimConfig
 * @since
 */
public class GimConfig {


    //host
    private String host;
    // 端口
    private Integer port;

    private Integer serverChunkSize=256 * 1024 * 1024;


    //自动重写
    private boolean autoRewrite = false;
    //重写次数，默认3
    private Integer reWriteNum = 3;
    //重发间隔，默认10s
    private Long reWriteDelay = Const.msg_delay;

    //是否开启心跳
    private boolean enableHeartBeat = false;
    //心跳间隔
    private Integer heartBeatInterval = Const.heartBeatInterval;

    //是否开启离线
    private boolean enableOffline = false;

    //集群
    private String serverId;
    private JedisPool jedisPool;
    private boolean enableCluster = false;


    public GimConfig host(String port) {
        this.host = host;
        return this;
    }

    public GimConfig port(int port) {
        this.port = port;
        return this;
    }

    public GimConfig serverChunkSize(int serverChunkSize) {
        this.serverChunkSize = serverChunkSize;
        return this;
    }



    public GimConfig autoRewrite(boolean autoRewrite) {
        this.autoRewrite = autoRewrite;
        return this;
    }

    public GimConfig reWriteNum(Integer reWriteNum) {
        this.reWriteNum = reWriteNum;
        return this;
    }

    public GimConfig reWriteDelay(Long millisecond) {
        this.reWriteDelay = millisecond;
        return this;
    }


    public GimConfig enableHeartBeat(boolean enableHeartBeat) {
        this.enableHeartBeat = enableHeartBeat;
        return this;
    }

    public GimConfig heartBeatInterval(Integer heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
        return this;
    }


    public GimConfig enableOffline(boolean enableOffline) {
        this.enableOffline = enableOffline;
        return this;
    }


    public GimConfig cluster(boolean enableCluster, String serverId, RedisProperties redisProperties) {
        this.enableCluster = enableCluster;
        this.serverId = serverId;
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if (redisProperties.getMaxTotal() != null) {
            jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        }
        if (redisProperties.getMaxIdle() != null) {
            jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        }
        if (redisProperties.getMaxWaitMillis() != null) {
            jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        }
        if (redisProperties.getTestOnBorrow() != null) {
            jedisPoolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());
        }
        if (redisProperties.getTestOnReturn() != null) {
            jedisPoolConfig.setTestOnReturn(redisProperties.getTestOnReturn());
        }
        if (redisProperties.getTestWhileIdle()) {
            jedisPoolConfig.setTestWhileIdle(redisProperties.getTestWhileIdle());
        }

        if (redisProperties.getHost() == null) {
            throw new NullPointerException("redis host is null");
        }

        if (redisProperties.getPort() == null) {
            throw new NullPointerException("redis port is null");
        }

        if (redisProperties.getPassword() == null) {
            throw new NullPointerException("redis password is null");
        }

        this.jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getConnectionTimeout(), redisProperties.getPassword());
        return this;
    }


    // ------------------------------------------------------

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getServerChunkSize() {
        return serverChunkSize;
    }

    public boolean isAutoRewrite() {
        return autoRewrite;
    }

    public Integer getReWriteNum() {
        return reWriteNum;
    }

    public Long getReWriteDelay() {
        return reWriteDelay;
    }

    public boolean isEnableHeartBeat() {
        return enableHeartBeat;
    }

    public Integer getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public boolean isEnableOffline() {
        return enableOffline;
    }


    public String getServerId() {
        return serverId;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public boolean isEnableCluster() {
        return enableCluster;
    }
}
