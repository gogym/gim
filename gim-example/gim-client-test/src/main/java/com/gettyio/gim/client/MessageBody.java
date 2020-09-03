package com.gettyio.gim.client;/*
 * 类名：ChatMessage
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/5/25
 */


import com.gettyio.gim.utils.FastJsonUtil;

public class MessageBody {

    /**
     * 消息类型
     */
    private Integer type;

    //-----------------------------------------------------

    private String senderId;

    private String senderName;

    private String senderHeadImgUrl;

    private String receiverId;

    private String receiverName;

    private String receiverHeadImgUrl;

    //-----------------------group--------------------------

    private String atUserId;

    //------------------------msg---------------------------

    private String body;

    private Integer bodyType;

    private Integer bodyLength;

    //------------------------------------------------------

    private Integer status;


    //-------------------------------------------------------


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderHeadImgUrl() {
        return senderHeadImgUrl;
    }

    public void setSenderHeadImgUrl(String senderHeadImgUrl) {
        this.senderHeadImgUrl = senderHeadImgUrl;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverHeadImgUrl() {
        return receiverHeadImgUrl;
    }

    public void setReceiverHeadImgUrl(String receiverHeadImgUrl) {
        this.receiverHeadImgUrl = receiverHeadImgUrl;
    }

    public String getAtUserId() {
        return atUserId;
    }

    public void setAtUserId(String atUserId) {
        this.atUserId = atUserId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getBodyType() {
        return bodyType;
    }

    public void setBodyType(Integer bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(Integer bodyLength) {
        this.bodyLength = bodyLength;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String json = FastJsonUtil.toJSONNoFeatures(this);
        return json;
    }

}
