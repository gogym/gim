package com.gettyio.gim.client.listener;


import com.gettyio.gim.client.core.GimContext;

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
