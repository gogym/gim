/*
 * 文件名：MessagEmitter.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.message;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.client.client.GimContext;
import com.gettyio.gim.client.common.Const;
import com.gettyio.gim.client.packet.MessageClass;
import com.gettyio.gim.client.utils.SnowflakeIdWorker;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessagEmitter {

    private GimContext gimContext;
    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(2, 1);

    public MessagEmitter(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * Description: send msg to user
     *
     * @param userId
     * @param msg
     * @see
     */
    public void sendToServer(String userId, MessageClass.Message msg) throws Exception {


    }


}
