package com.gettyio.gim.server;/*
 * 类名：GimHost
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/8/12
 */

public class GimHost {

    /**
     * host
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;

    /**
     * 连接类型
     */
    private Integer socketType = SocketType.SOCKET;

    public GimHost(Integer port) {
        this.port = port;
    }

    public GimHost(Integer port, Integer socketType) {
        this.port = port;
        this.socketType = socketType;
    }

    public GimHost(String host, Integer port, Integer socketType) {
        this.host = host;
        this.port = port;
        this.socketType = socketType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getSocketType() {
        return socketType;
    }

    public void setSocketType(Integer socketType) {
        this.socketType = socketType;
    }
}
