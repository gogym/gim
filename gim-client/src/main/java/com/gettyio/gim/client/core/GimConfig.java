package com.gettyio.gim.client.core;

import com.gettyio.gim.client.comm.Const;

/**
 * 〈配置类〉
 *
 * @author gogym
 * @version 2019年7月12日
 * @see GimConfig
 * @since
 */
public class GimConfig {


    private String host;
    private Integer port;

    //自动重写
    private boolean autoRewrite = false;
    //重写次数，默认3
    private Integer reWriteNum = 3;
    //重发间隔，默认10s
    private Long reWriteDelay = Const.msgDelay;

    //是否开启心跳
    private boolean enableHeartBeat = false;
    //心跳间隔
    private Long heartBeatInterval = Const.heartBeatInterval;

    //是否自动重连
    private boolean enableReConnect = false;


    public GimConfig host(String host) {
        this.host = host;
        return this;
    }

    public GimConfig port(int port) {
        this.port = port;
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

    public GimConfig heartBeatInterval(Long heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
        return this;
    }


    public GimConfig enableReConnect(boolean enableReConnect) {
        this.enableReConnect = enableReConnect;
        return this;
    }

    // ------------------------------------------------------

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
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

    public Long getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public boolean isEnableReConnect() {
        return enableReConnect;
    }
}
