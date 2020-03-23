package com.gettyio.gim.listener;


import com.gettyio.gim.server.GimContext;

/**
 * 〈消息回调监听〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see ChatListener
 * @since
 */
public interface ChannelStatusListener {

    void channelAdd(GimContext gimContext, String address);

    void channelClose(String channelId);

}
