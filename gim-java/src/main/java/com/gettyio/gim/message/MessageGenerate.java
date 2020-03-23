package com.gettyio.gim.message;

import com.gettyio.gim.common.Const;
import com.gettyio.gim.common.Type;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.packet.MessageClass.Message;
import com.gettyio.gim.utils.SnowflakeIdWorker;

import java.util.Date;
import java.util.List;

/**
 * 消息构造类
 *
 * @author gogym
 * @version 2019年6月12日
 * @see MessageGenerate
 * @since
 */
public class MessageGenerate {

    static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);


    private static MessageClass.Message.Builder CreateMessageBuilder(int reqType) {
        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setIdentify(Const.identify);
        builder.setVersion(Const.version);
        builder.setReqType(reqType);
        builder.setMsgTime(new Date().getTime());
        builder.setId(String.valueOf(idWorker.nextId()));
        if (Const.serverId != null) {
            builder.setSenderId(Const.serverId);
        }
        return builder;
    }


    /**
     * Description: 创建ack消息
     *
     * @param ack
     * @return
     * @see
     */
    public static Message createAck(String ack) {

        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.ACK_REQ);
        // 创建一个ack
        builder.setAck(ack);
        // 把ack消息放到消息body里
        return builder.build();
    }


    /**
     * Description: 创建用户绑定响应信息
     *
     * @param senderId
     * @param result
     * @param msg
     * @return
     * @see
     */
    public static Message crateConnectResp(String senderId) {
        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.CONNET_RESP);
        builder.setSenderId(senderId);
        builder.setResult(Const.success);
        builder.setBody("connect success");
        return builder.build();
    }

    /**
     * 添加好友请求
     *
     * @return com.gettyio.gim.packet.MessageClass.Message
     * @params [senderId, receiverId, status]
     */
    public static Message createAddFriendReq(String senderId, String senderName, String senderHeadImgUrl, String receiverId) {
        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.ADD_FRIEND_REQ);
        builder.setSenderId(senderId);
        builder.setSenderName(senderName);
        builder.setSenderHeadImgUrl(senderHeadImgUrl + "");
        builder.setReceiverId(receiverId);
        return builder.build();
    }

    /**
     * 创建好友添加响应
     *
     * @return com.gettyio.gim.packet.MessageClass.Message
     * @params [senderId, receiverId, status]
     */
    public static Message createAddFriendResp(String senderId, String receiverId, Integer status) {
        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.ADD_FRIEND_RESP);
        builder.setSenderId(senderId);
        builder.setReceiverId(receiverId);
        builder.setStatus(status);
        return builder.build();
    }


    /**
     * Description: 创建单聊消息
     *
     * @param sendlerId
     * @param receiverId
     * @param msgType
     * @param body
     * @return
     * @see
     */
    public static Message createSingleChatReq(String sendlerId, String receiverId, int msgType, String body) {

        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.SINGLE_MSG_REQ);
        builder.setSenderId(sendlerId);
        builder.setReceiverId(receiverId);
        builder.setBodyType(msgType);
        builder.setBody(body);
        return builder.build();

    }

    /**
     * Description: 创建一个团消息
     *
     * @param sendlerId
     * @param groupId
     * @param msgType
     * @param body
     * @param atUserId
     * @return
     * @see
     */
    public static Message createGroupChatReq(String sendlerId, String groupId, int msgType, String body, List<String> atUserId) {

        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.GROUP_MSG_REQ);
        builder.setSenderId(sendlerId);
        builder.setGroupId(groupId);
        builder.setBodyType(msgType);
        builder.setBody(body);

        if (atUserId != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String string : atUserId) {
                stringBuffer.append(string).append(",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            builder.setAtUserId(stringBuffer.toString());
        }
        return builder.build();

    }


}
