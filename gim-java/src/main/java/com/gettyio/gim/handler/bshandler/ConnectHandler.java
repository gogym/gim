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

package com.gettyio.gim.handler.bshandler;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.bind.GimBind;
import com.gettyio.gim.common.Const;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.ConnectReqClass;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;

/**
 * 连接请求处理器
 *
 * @author gogym
 * @version 2019年6月11日
 * @see ConnectHandler
 * @since
 */
public class ConnectHandler extends AbsChatHandler<ConnectReqClass.ConnectReq> {

    private GimContext gimContext;

    public ConnectHandler(GimContext gimContext) {
        this.gimContext = gimContext;
    }


    @Override
    public Class<ConnectReqClass.ConnectReq> bodyClass() {
        return ConnectReqClass.ConnectReq.class;
    }

    @Override
    public void handler(MessageClass.Message message, ConnectReqClass.ConnectReq bsBody, AioChannel aioChannel) throws Exception {

        // 发送者的ID
        String senderId = bsBody.getSenderId();
        // 绑定用户关系
        gimContext.gimBind.bindUser(senderId, aioChannel);
        MessageClass.Message reMsg = MessageGenerate.crateConnectResp(senderId, Const.success, "connect success");
        // 写响应消息到客户端
        aioChannel.writeAndFlush(reMsg);
    }

}
