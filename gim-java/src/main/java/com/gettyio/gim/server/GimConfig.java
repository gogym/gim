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


    public GimConfig host(String port) {
        this.host = host;
        return this;
    }

    public GimConfig port(int port) {
        this.port = port;
        return this;
    }


    // ------------------------------------------------------

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }


}
