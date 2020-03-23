/*
 * 文件名：MessageGenerate.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.client.message;

import com.gettyio.gim.client.comm.Const;
import com.gettyio.gim.client.comm.Type;
import com.gettyio.gim.client.packet.*;
import com.gettyio.gim.client.utils.SnowflakeIdWorker;

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
        return builder;
    }


    /**
     * Description: 创建心跳消息
     *
     * @param ack
     * @return
     * @see
     */
    public static MessageClass.Message createHeartBeat() {
        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.HEART_BEAT_REQ);
        return builder.build();
    }


    /**
     * Description: 创建ack消息
     *
     * @param ack
     * @return
     * @see
     */
    public static MessageClass.Message createAck(String ack) {

        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.ACK_REQ);
        // 创建一个ack
        builder.setAck(ack);
        // 把ack消息放到消息body里
        return builder.build();
    }

    /**
     * Description: 创建用户绑定信息
     *
     * @return
     * @see
     */
    public static MessageClass.Message crateConnectReq(String senderId) {
        MessageClass.Message.Builder builder = CreateMessageBuilder(Type.CONNET_REQ);
        builder.setSenderId(senderId);
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
    public static MessageClass.Message createSingleChatReq(String sendlerId, String receiverId, int msgType, String body) {
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
    public static MessageClass.Message createGroupChatReq(String sendlerId, String groupId, int msgType, String body, List<String> atUserId) {

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
