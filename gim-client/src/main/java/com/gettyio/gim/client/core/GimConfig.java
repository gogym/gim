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
package com.gettyio.gim.client.core;

import com.gettyio.gim.comm.ClientAuth;
import com.gettyio.gim.comm.Const;


/**
 * GimConfig.java
 *
 * @description:配置类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class GimConfig {


    /**
     * host
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;

    /**
     * 总缓冲区大小上限
     */
    private Integer clientChunkSize = 256 * 1024 * 1024;

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
     * 是否自动重连
     */
    private boolean enableReConnect = false;

    //----------------------------------ssl---------------------------------
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

    private boolean clientMode = true;
    private boolean clientAuth = ClientAuth.NONE;

    //----------------------------------ssl end---------------------------------

    public GimConfig host(String host) {
        this.host = host;
        return this;
    }

    public GimConfig port(int port) {
        this.port = port;
        return this;
    }

    public GimConfig clientChunkSize(int clientChunkSize) {
        this.clientChunkSize = clientChunkSize;
        return this;
    }


    public GimConfig autoRewrite(boolean autoRewrite) {
        this.autoRewrite = autoRewrite;
        return this;
    }

    public GimConfig autoRewrite(boolean autoRewrite,Integer reWriteNum) {
        this.autoRewrite = autoRewrite;
        this.reWriteNum = reWriteNum;
        return this;
    }

    public GimConfig autoRewrite(boolean autoRewrite,Integer reWriteNum,Long millisecond) {
        this.autoRewrite = autoRewrite;
        this.reWriteNum = reWriteNum;
        this.reWriteDelay = millisecond;
        return this;
    }


    public GimConfig enableHeartBeat(boolean enableHeartBeat) {
        this.enableHeartBeat = enableHeartBeat;
        return this;
    }

    public GimConfig enableHeartBeat(boolean enableHeartBeat,Integer heartBeatInterval) {
        this.enableHeartBeat = enableHeartBeat;
        this.heartBeatInterval = heartBeatInterval;
        return this;
    }


    public GimConfig enableReConnect(boolean enableReConnect) {
        this.enableReConnect = enableReConnect;
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

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getClientChunkSize() {
        return clientChunkSize;
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

    public boolean isEnableReConnect() {
        return enableReConnect;
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
