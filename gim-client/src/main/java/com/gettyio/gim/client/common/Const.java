package com.gettyio.gim.client.common;

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
    // Repost message in milliseconds
    public static final Long msg_delay = 20 * 1000L;

    // The group created put in the key of redis
    public final static String REDIS_CHAT_GROUP = "GIM_CHAT_GROUP_";

    public static final String CHARSET = "utf-8";

    public static final String netty_attributeKey = "netty.channel";

    public static final Integer success = 10000;

    public static final Integer fail = 10001;

}
