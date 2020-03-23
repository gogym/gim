package com.gettyio.gim.client.message;

import com.gettyio.gim.client.comm.MessageContentType;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.client.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.List;

public class MessagEmitter {

    private GimContext gimContext;

    public MessagEmitter(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * Description: send msg to server
     *
     * @param msg
     * @see
     */
    public void send(MessageClass.Message msg) {

        if (gimContext.gimConfig.isAutoRewrite()) {
            //如果开启了重发
            MessageDelayPacket mdp = new MessageDelayPacket(msg, gimContext.gimConfig.getReWriteDelay());
            gimContext.delayMsgQueue.put(mdp);
        }
        //注意，要在加入重发队列后在发到服务器。否则ACK返回后，还没有加入到队列，就会造成一次无意义的重发
        sendNoCallBack(msg);

        //发送消息回调
        try {
            String msgJson = JsonFormat.printer().print(msg);
            if(gimContext.channelWriteListener!=null){
                gimContext.channelWriteListener.channelWrite(msgJson);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }


    public void sendNoCallBack(MessageClass.Message msg) {
        if (!gimContext.aioChannel.isInvalid()) {
            gimContext.aioChannel.writeAndFlush(msg);
        }
    }


    /**
     * 发送心跳消息
     *
     * @return void
     * @params []
     */
    public void sendHeartBeat() {
        MessageClass.Message msg = MessageGenerate.createHeartBeat();
        sendNoCallBack(msg);
    }


    /**
     * 发送用户绑定消息
     *
     * @return void
     * @params [userId]
     */
    public void sendConnectReq(String userId) {
        MessageClass.Message msg = MessageGenerate.crateConnectReq(userId);
        sendNoCallBack(msg);
    }


    /**
     * 发送单聊文本消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendSingleChatText(String sendlerId, String receiverId, String text) {
        MessageClass.Message msg = MessageGenerate.createSingleChatReq(sendlerId, receiverId, MessageContentType.text.getValue(), text);
        send(msg);
    }


    /**
     * 发送单聊图片消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendSingleChatImg(String sendlerId, String receiverId, String path) {
        MessageClass.Message msg = MessageGenerate.createSingleChatReq(sendlerId, receiverId, MessageContentType.image.getValue(), path);
        send(msg);
    }


    /**
     * 发送单聊语音消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendSingleChatAudio(String sendlerId, String receiverId, String audioBase64) {
        MessageClass.Message msg = MessageGenerate.createSingleChatReq(sendlerId, receiverId, MessageContentType.audio.getValue(), audioBase64);
        send(msg);
    }

    /**
     * 发送群聊文本消息
     *
     * @return void
     * @params [sendlerId, receiverId, text]
     */
    public void sendGroupChatText(String sendlerId, String groupId, String text, List<String> atUserId) {
        MessageClass.Message msg = MessageGenerate.createGroupChatReq(sendlerId, groupId, MessageContentType.text.getValue(), text, atUserId);
        send(msg);
    }


    /**
     * 发送群聊图片消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendGroupChatImg(String sendlerId, String groupId, String path) {
        MessageClass.Message msg = MessageGenerate.createGroupChatReq(sendlerId, groupId, MessageContentType.image.getValue(), path, null);
        send(msg);
    }


    /**
     * 发送群聊语音消息
     *
     * @return void
     * @params [sendlerId, receiverId, path]
     */
    public void sendGroupChatAudio(String sendlerId, String groupId, String audioBase64) {
        MessageClass.Message msg = MessageGenerate.createGroupChatReq(sendlerId, groupId, MessageContentType.audio.getValue(), audioBase64, null);
        send(msg);
    }


}
