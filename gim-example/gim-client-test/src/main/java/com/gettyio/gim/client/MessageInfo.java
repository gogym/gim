package com.gettyio.gim.client;

import java.io.Serializable;
import java.util.List;

/**
 * MessageInfo.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/22
 * @copyright: Copyright by gettyio.com
 */
public class MessageInfo implements Serializable {

    public static final long serialVersionUID = 13213523256453L;

    private String id;

    private String identify;

    private String version;

    private Integer reqType;

    private Long msgTime;

    private String msgId;

    private String serverId;

    //----------------------ack-----------------------------

    private String ack;

    //-----------------------user---------------------------

    private String senderId;

    private String senderName;

    private String senderHeadImgUrl;

    private String receiverId;

    private String receiverName;

    private String receiverHeadImgUrl;

    //-----------------------group--------------------------

    private String groupId;

    private String groupName;

    private String groupHeadImgUrl;

    private String atUserId;

    //------------------------msg---------------------------

    private String body;

    private Integer bodyType;

    private Integer bodyLength;

//------------------------------------------------------

    private Integer status;

    private Integer result;

    private String field1;

    private String field2;

    private String field3;

    private byte[] field4;

    private List<String> field5;


//----------------------------------------------------------


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHeadImgUrl() {
        return groupHeadImgUrl;
    }

    public void setGroupHeadImgUrl(String groupHeadImgUrl) {
        this.groupHeadImgUrl = groupHeadImgUrl;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public byte[] getField4() {
        return field4;
    }

    public void setField4(byte[] field4) {
        this.field4 = field4;
    }

    public List<String> getField5() {
        return field5;
    }

    public void setField5(List<String> field5) {
        this.field5 = field5;
    }
}
