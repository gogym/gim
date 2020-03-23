package com.gettyio.gim.common;

/**
 * @author gogym
 * @version 2019年7月26日
 * @see Const
 * @since
 */
public class Const {

    public static final Integer success = 10000;
    public static final Integer fail = 10001;

    // Protocol identify
    public final static String identify = "com/gettyio/gim";
    // Protocol version
    public final static String version = "1.0";
    // Repost message in milliseconds
    public static final Long msg_delay = 10 * 1000L;
    //心跳检查间隔，默认30s
    public static final Integer heartBeatInterval = 30;

    public static String serverId;

}
