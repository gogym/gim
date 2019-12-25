/*
 * 文件名：HeartBeatHandler.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.handler.bshandler;


import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.handler.AbsChatHandler;
import com.gettyio.gim.packet.HeartBeatReqClass;
import com.gettyio.gim.packet.MessageClass;

public class HeartBeatHandler extends AbsChatHandler<HeartBeatReqClass.HeartBeatReq> {

    @Override
    public Class<HeartBeatReqClass.HeartBeatReq> bodyClass() {
        return HeartBeatReqClass.HeartBeatReq.class;

    }

    @Override
    public void handler(MessageClass.Message message, HeartBeatReqClass.HeartBeatReq bsBody, AioChannel aioChannel) {


    }

}
