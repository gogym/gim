/*
 * 文件名：ChatMsgListener.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.listener;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.packet.MessageClass;

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
    public void read(MessageClass.Message message, AioChannel aioChannel) throws Exception;

}
