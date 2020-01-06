/*
 * 文件名：GimListenerIntf.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年7月29日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.listener;


import com.gettyio.gim.client.packet.MessageClass;

/**
 * 〈消息回调监听〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see ChatListener
 * @since
 */
public interface GimListener {

    public void channelRead(String address, MessageClass.Message message);

    public void channelWrite(boolean isSuccess, MessageClass.Message message);

    public void channelAdd(String address);

    public void channelClose(String address);

}
