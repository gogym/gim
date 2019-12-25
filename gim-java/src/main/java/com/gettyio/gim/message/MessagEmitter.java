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

package com.gettyio.gim.message;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.common.Const;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.gettyio.gim.utils.SnowflakeIdWorker;

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
    public void sendToUser(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);

        if (channelId != null) {
            AioChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            MessageDelayPacket mdp = new MessageDelayPacket(userId, msg, Const.msg_delay);
            gimContext.delayMsgQueue.put(mdp);
            channel.writeAndFlush(msg);
        }

    }

    /**
     * Description: send msg to group
     *
     * @param groupId
     * @param msg
     * @throws Exception
     * @see
     */
    public void sendToGroup(String groupId, MessageClass.Message msg) throws Exception {

        ConcurrentMap<String, CopyOnWriteArrayList<String>> groupUserMap = gimContext.groupUserMap;
        CopyOnWriteArrayList<String> list = groupUserMap.get(groupId);

        if (groupUserMap.get(groupId) != null) {
            for (String string : list) {
                sendToUser(string, msg.toBuilder().setId(String.valueOf(idWorker.nextId())).build());
            }
        }
    }

}
