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

import com.gettyio.gim.client.common.Const;
import com.gettyio.gim.client.common.Type;
import com.gettyio.gim.client.packet.*;
import com.gettyio.gim.client.utils.SnowflakeIdWorker;
import com.google.protobuf.Any;

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
    public static MessageClass.Message createSingleChatReq(String sendlerId,
                                                           String receiverId, int msgType, String body) {

        // 创建一个ack消息
        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setIdentify(Const.identify);
        builder.setVersion(Const.version);
        builder.setReqType(Type.SINGLE_MSG_REQ);
        builder.setMsgTime(new Date().getTime());
        builder.setId(String.valueOf(idWorker.nextId()));

        SingleChatReqClass.SingleChatReq.Builder singleChatReq = SingleChatReqClass.SingleChatReq.newBuilder();
        singleChatReq.setSenderId(sendlerId);
        singleChatReq.setReceiverId(receiverId);
        singleChatReq.setMsgType(msgType);
        singleChatReq.setBody(body);

        builder.setBody(Any.pack(singleChatReq.build()));
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
    public static MessageClass.Message createGroupChatReq(String sendlerId, String groupId,
                                                          int msgType, String body, List<String> atUserId) {

        // 创建一个ack消息
        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setIdentify(Const.identify);
        builder.setVersion(Const.version);
        builder.setReqType(Type.GROUP_MSG_REQ);
        builder.setMsgTime(new Date().getTime());
        builder.setId(String.valueOf(idWorker.nextId()));

        GroupChatReqClass.GroupChatReq.Builder groupChatReq = GroupChatReqClass.GroupChatReq.newBuilder();
        groupChatReq.setSenderId(sendlerId);
        groupChatReq.setGroupId(groupId);
        groupChatReq.setMsgType(msgType);
        groupChatReq.setBody(body);

        if (atUserId != null) {

            StringBuffer stringBuffer = new StringBuffer();

            for (String string : atUserId) {
                stringBuffer.append(string).append(",");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            groupChatReq.setAtUserId(stringBuffer.toString());
        }

        builder.setBody(Any.pack(groupChatReq.build()));
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

        // 创建一个ack消息
        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setIdentify(Const.identify);
        builder.setVersion(Const.version);
        builder.setReqType(Type.ACK_REQ);
        builder.setMsgTime(new Date().getTime());
        builder.setId(String.valueOf(idWorker.nextId()));

        // 创建一个ack
        AckReqClass.AckReq.Builder ackBuilder = AckReqClass.AckReq.newBuilder();
        ackBuilder.setAck(ack);
        // 把ack消息放到消息body里
        builder.setBody(Any.pack(ackBuilder.build()));
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
    public static MessageClass.Message crateConnectReq(String senderId) {

        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setIdentify(Const.identify);
        builder.setVersion(Const.version);
        builder.setReqType(Type.CONNET_REQ);
        builder.setMsgTime(new Date().getTime());
        builder.setId(String.valueOf(idWorker.nextId()));

        ConnectReqClass.ConnectReq.Builder respBuilder = ConnectReqClass.ConnectReq.newBuilder();
        respBuilder.setSenderId(senderId);
        builder.setBody(Any.pack(respBuilder.build()));
        return builder.build();
    }
}
