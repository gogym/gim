package com.gettyio.gim.message;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.common.Const;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessagEmitter {

    private GimContext gimContext;

    public MessagEmitter(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * 发送消息，会加入重写确认，离线
     *
     * @param userId
     * @param msg
     * @see
     */
    public void sendToUser(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);

        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            //放入重发队列中
            MessageDelayPacket mdp = new MessageDelayPacket(msg, Const.msg_delay);
            gimContext.delayMsgQueue.put(mdp);

            channel.writeAndFlush(msg);
            return;
        } else if (gimContext.gimConfig.isEnableCluster()) {
            String serverId = gimContext.clusterRoute.getUserRoute(userId);
            if (serverId != null) {
                MessageDelayPacket mdp = new MessageDelayPacket(msg, Const.msg_delay);
                gimContext.delayMsgQueue.put(mdp);
                //查找服务路由
                gimContext.clusterRoute.sendToCluster(msg, serverId);
                return;
            }

        }

        if (gimContext.offlineMsgIntf != null) {
            //离线消息
            String msgJson = JsonFormat.printer().print(msg);
            gimContext.offlineMsgIntf.offlineMsg(msgJson);
        }
    }


    /**
     * 发送但不加入重写队列
     *
     * @param userId
     * @param msg
     * @return void
     */
    public void sendToUserNoReWrite(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);

        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
            channel.writeAndFlush(msg);
            return;
        } else if (gimContext.gimConfig.isEnableCluster()) {
            String serverId = gimContext.clusterRoute.getUserRoute(userId);
            if (serverId != null) {
                //查找服务路由
                gimContext.clusterRoute.sendToCluster(msg, serverId);
                return;
            }

        }

        if (gimContext.offlineMsgIntf != null) {
            //离线消息
            String msgJson = JsonFormat.printer().print(msg);
            gimContext.offlineMsgIntf.offlineMsg(msgJson);
        }
    }


    /**
     * 单纯发送给用户
     *
     * @param userId
     * @param msg
     * @return void
     */
    public void sendToUserOnly(String userId, MessageClass.Message msg) throws Exception {

        String channelId = gimContext.userChannelMap.get(userId);
        if (channelId != null) {
            SocketChannel channel = gimContext.channels.find(channelId);
            if (channel == null) {
                throw new Exception("[channel is null error]");
            }
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

        if (msg.getServerId() != null && !msg.getServerId().equals("")) {
            //如果消息服务器ID不等于空，则这条消息是通过集群路由过来的。此时应直接在本机找到对应的连接
            sendToUser(msg.getReceiverId(), msg);
        } else {
            // 先判断是否开启集群
            if (gimContext.gimConfig.isEnableCluster()) {
                Set<String> set = gimContext.clusterRoute.getGroupRoute(groupId);
                if (set != null) {
                    for (String string : set) {
                        //发送时把群消息接收者ID设置进去
                        MessageClass.Message.Builder builder = msg.toBuilder().setReceiverId(string);
                        sendToUser(string, builder.build());
                    }
                }
            } else {
                CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
                if (list != null) {
                    for (String string : list) {
                        //发送时把群消息接收者ID设置进去
                        MessageClass.Message.Builder builder = msg.toBuilder().setReceiverId(string);
                        sendToUser(string, builder.build());
                    }
                }
            }
        }


    }


    /**
     * 发送用户绑定消息成功结果
     *
     * @return void
     * @params [userId]
     */
    public void sendConnectResp(String userId) throws Exception {
        MessageClass.Message msg = MessageGenerate.crateConnectResp(userId);
        sendToUserOnly(userId, msg);
    }


    /**
     * 发送单聊消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendSingleChatMsg(String sendlerId, String receiverId, String content, Integer contentType) throws Exception {
        MessageClass.Message msg = MessageGenerate.createSingleChatReq(sendlerId, receiverId, contentType, content);
        sendToUser(receiverId, msg);
    }


    /**
     * 发送群聊消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendGroupChatMsg(String sendlerId, String groupId, String content, Integer contentType, List<String> atUserId) throws Exception {
        MessageClass.Message msg = MessageGenerate.createGroupChatReq(sendlerId, groupId, contentType, content, atUserId);
        sendToGroup(groupId, msg);
    }


}
