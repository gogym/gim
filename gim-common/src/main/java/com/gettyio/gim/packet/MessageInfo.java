package com.gettyio.gim.packet;

import java.io.Serializable;

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

    private String serverId;

    //----------------------ack-----------------------------

    private String ack;

    //-----------------------user---------------------------

    private String fromId;

    private String toId;


    //------------------------msg---------------------------

    private String body;

    //-----------------------------------------------------

    private Integer status;


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


    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
