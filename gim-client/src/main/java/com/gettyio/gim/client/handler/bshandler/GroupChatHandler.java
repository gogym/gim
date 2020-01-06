/*
 * 文件名：ConcentHandler.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.handler.bshandler;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.client.handler.AbsChatHandler;
import com.gettyio.gim.client.packet.GroupChatReqClass;
import com.gettyio.gim.client.packet.MessageClass;

/**
 * 群聊处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see GroupChatHandler
 * @since
 */
public class GroupChatHandler extends AbsChatHandler<GroupChatReqClass.GroupChatReq> {

    private GimContext gimContext;

    public GroupChatHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<GroupChatReqClass.GroupChatReq> bodyClass() {
        return GroupChatReqClass.GroupChatReq.class;
    }

    @Override
    public void handler(MessageClass.Message message, GroupChatReqClass.GroupChatReq bsBody, AioChannel aioChannel) throws Exception {


    }

}
