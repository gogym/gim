/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gettyio.gim.server;


import com.gettyio.gim.comm.ClientAuth;
import com.gettyio.gim.redis.RedisProperties;
import com.gettyio.gim.comm.Const;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * GimConfig.java
 *
 * @description:配置类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimConfig {


    private List<GimHost> hostList = new ArrayList<>();


    /**
     * 内存池上限
     */
    private Integer serverChunkSize = 256 * 1024 * 1024;
    /**
     * 自动重发
     */
    private boolean autoRewrite = false;
    /**
     * 重发次数，默认3
     */
    private Integer reWriteNum = 3;
    /**
     * 重发间隔，默认10s
     */
    private Long reWriteDelay = Const.MSG_DELAY;
    /**
     * 是否开启心跳
     */
    private boolean enableHeartBeat = true;
    /**
     * 心跳间隔
     */
    private Integer heartBeatInterval = Const.HEARTBEAT_INTERVAL;
    /**
     * 是否开启离线
     */
    private boolean enableOffline = true;
    /**
     * 集群
     */
    private String serverId;
    private JedisPool jedisPool;
    /**
     * 默认不开启
     */
    private boolean enableCluster = false;
    /**
     * ssl
     */
    private boolean enableSsl = false;
    private String pkPath;
    private String keyPassword;
    private String keystorePassword;
    /**
     * trust不设置也不会影响ssl的使用
     */
    private String trustPath;
    private String trustPassword;

    private boolean clientMode = false;
    private boolean clientAuth = ClientAuth.NONE;


    //---------------------------------------------------------------------------------------------------


    public GimConfig hosts(List<GimHost> hostList) {
        this.hostList.addAll(hostList);
        return this;
    }

    public GimConfig addHost(GimHost host) {
        this.hostList.add(host);
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

        if (enableCluster) {
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
        }
        return this;
    }


    public GimConfig openSsl(String pkPath, String keyPassword, String keystorePassword, boolean clientAuth) {
        this.enableSsl = true;
        this.pkPath = pkPath;
        this.keyPassword = keyPassword;
        this.keystorePassword = keystorePassword;
        this.clientAuth = clientAuth;
        return this;
    }

    public GimConfig openSsl(String pkPath, String keyPassword, String keystorePassword, boolean clientAuth, String trustPath, String trustPassword) {
        this.enableSsl = true;
        this.pkPath = pkPath;
        this.keyPassword = keyPassword;
        this.keystorePassword = keystorePassword;
        this.clientAuth = clientAuth;
        this.trustPath = trustPath;
        this.trustPassword = trustPassword;
        return this;
    }

    // ------------------------------------------------------

    public List<GimHost> getHosts() {
        return hostList;
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

    public boolean isEnableSsl() {
        return enableSsl;
    }

    public String getPkPath() {
        return pkPath;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getTrustPath() {
        return trustPath;
    }

    public String getTrustPassword() {
        return trustPassword;
    }

    public boolean isClientMode() {
        return clientMode;
    }

    public boolean isClientAuth() {
        return clientAuth;
    }
}
