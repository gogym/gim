/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gettyio.gim.message;

import com.gettyio.gim.comm.Const;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.packet.MessageClass.Message;
import com.gettyio.gim.utils.SnowflakeIdWorker;

/**
 * MessageGenerate.java
 *
 * @description:消息构造类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class MessageGenerate {

    private static SnowflakeIdWorker idWorker;
    private static MessageGenerate messageGenerate;

    private MessageGenerate() {
        idWorker = new SnowflakeIdWorker(1, 1);
    }

    public static MessageGenerate getInstance() {
        if (messageGenerate == null) {
            //保证异步处理安全操作
            synchronized (MessageGenerate.class) {
                if (messageGenerate == null) {
                    messageGenerate = new MessageGenerate();
                }
            }
        }
        return messageGenerate;
    }


    /**
     * MessageGenerate.java
     *
     * @description:构造消息
     * @author:gogym
     * @date:2020/4/10
     * @copyright: Copyright by gettyio.com
     */
    private Message.Builder CreateMessageBuilder(int reqType) {
        Message.Builder builder = Message.newBuilder();

        builder.setReqType(reqType);
        builder.setMsgTime(System.currentTimeMillis());
        builder.setId(String.valueOf(idWorker.nextId()));
        return builder;
    }


    /**
     * Description: 创建ack消息
     *
     * @param ack
     * @return
     * @see
     */
    public Message createAck(String ack) {
        Message.Builder builder = CreateMessageBuilder(Type.ACK_REQ);
        // 创建一个ack
        builder.setAck(ack);
        // 把ack消息放到消息body里
        return builder.build();
    }


    /**
     * Description: 创建心跳消息
     *
     * @return
     * @see
     */
    public Message createHeartBeat() {
        Message.Builder builder = CreateMessageBuilder(Type.HEART_BEAT_REQ);
        return builder.build();
    }


    /**
     * 创建绑定请求信息
     *
     * @param fromId
     * @return
     */
    public Message createBindReq(String fromId) {
        Message.Builder builder = CreateMessageBuilder(Type.BIND_REQ);
        builder.setFromId(fromId);
        return builder.build();
    }


    /**
     * Description: 创建绑定响应信息
     *
     * @param fromId
     * @return
     * @see
     */
    public Message createBindResp(String fromId) {
        Message.Builder builder = CreateMessageBuilder(Type.BIND_RESP);
        builder.setFromId(fromId);
        builder.setStatus(Const.SUCCESS);
        return builder.build();
    }


    /**
     * 解绑请求信息
     *
     * @param fromId
     * @return
     */
    public Message createUnbindReq(String fromId) {
        Message.Builder builder = CreateMessageBuilder(Type.UNBIND_REQ);
        builder.setFromId(fromId);
        return builder.build();
    }

    /**
     * Description: 解绑响应信息
     *
     * @param fromId
     * @return
     * @see
     */
    public Message createUnbindResp(String fromId) {
        Message.Builder builder = CreateMessageBuilder(Type.UNBIND_RESP);
        builder.setFromId(fromId);
        builder.setStatus(Const.SUCCESS);
        return builder.build();
    }


    /**
     * 创建单聊消息
     *
     * @param fromId
     * @param toId
     * @param body
     * @return
     */
    public Message createSingleMsgReq(String fromId, String toId, String body) {
        Message.Builder builder = CreateMessageBuilder(Type.SINGLE_MSG_REQ);
        if (fromId != null) {
            builder.setFromId(fromId);
        }
        if (toId != null) {
            builder.setToId(toId);
        }
        if (body != null) {
            builder.setBody(body);
        }
        return builder.build();
    }


    /**
     * Description: 创建一个群消息
     *
     * @param fromId
     * @param toId
     * @param body
     * @return
     * @see
     */

    public Message createGroupMsgReq(String fromId, String toId, String body) {

        Message.Builder builder = CreateMessageBuilder(Type.GROUP_MSG_REQ);
        if (fromId != null) {
            builder.setFromId(fromId);
        }
        if (toId != null) {
            builder.setToId(toId);
        }
        if (body != null) {
            builder.setBody(body);
        }
        return builder.build();
    }


    /**
     * @param fromId
     * @param toId
     * @param body
     * @param reqType
     * @return
     */
    public Message createMessage(String fromId, String toId, String body, Integer reqType) {
        Message.Builder builder = CreateMessageBuilder(reqType);
        if (fromId != null) {
            builder.setFromId(fromId);
        }
        if (toId != null) {
            builder.setToId(toId);
        }
        if (body != null) {
            builder.setBody(body);
        }
        return builder.build();
    }


    /**
     * @param fromId
     * @param toId
     * @param body
     * @param reqType
     * @param status
     * @return
     */
    public Message createMessage(String fromId, String toId, String body, Integer reqType, Integer status) {
        Message.Builder builder = CreateMessageBuilder(reqType);
        if (fromId != null) {
            builder.setFromId(fromId);
        }
        if (toId != null) {
            builder.setToId(toId);
        }
        if (body != null) {
            builder.setBody(body);
        }

        if (status != null) {
            builder.setStatus(status);
        }

        return builder.build();
    }

}
