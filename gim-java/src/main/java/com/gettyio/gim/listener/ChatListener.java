package com.gettyio.gim.listener;


import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.packet.MessageClass;

/**
 * 〈消息读取监听〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see ChatListener
 * @since
 */
public interface ChatListener {

    /**
     * Description: 实现该方法，获取消息
     *
     * @param message
     * @see
     */
    void read(MessageClass.Message message, SocketChannel socketChannel) throws Exception;

}
