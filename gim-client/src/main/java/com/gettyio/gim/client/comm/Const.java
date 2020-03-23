package com.gettyio.gim.client.comm;

/**
 * 〈constant class〉
 *
 * @author gogym
 * @version 2019年7月26日
 * @see Const
 * @since
 */
public class Const {
    // Protocol identify
    public final static String identify = "gim";
    // Protocol version
    public final static String version = "1.0";
    //消息重发间隔，默认10s
    public static final Long msgDelay = 10 * 1000L;
    //心跳间隔，默认30s
    public static final Long heartBeatInterval = 30 * 1000L;

    public static final Integer success = 10000;

    public static final Integer fail = 10001;

}
