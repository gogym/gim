package com.gettyio.gim.client.intf;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.packet.MessageClass;

/**
 * 〈消息业务处理〉
 *
 * @author gogym
 * @version 2019年6月11日
 * @see ChatHandlerIntf
 * @since
 */
public interface ChatHandlerIntf {

    /**
     * Description: 业务处理接口
     *
     * @param
     * @return
     * @throws
     * @see
     */
    void handler(MessageClass.Message message, AioChannel aioChannel) throws Exception;

}
