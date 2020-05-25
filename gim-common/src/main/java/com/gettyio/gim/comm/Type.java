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
package com.gettyio.gim.comm;


/**
 * Type.java
 *
 * @description:消息类型定义
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public interface Type {

    /**
     * 用户绑定请求
     */
    int BIND_REQ = 1;
    /**
     * 用户绑定响应
     */
    int BIND_RESP = 2;

    /**
     * 解绑用户请求
     */
    int UNBIND_REQ = 3;
    /**
     * 解绑用户响应
     */
    int UNBIND_RESP = 4;

    /**
     * 进入群组请求
     */
    int JOIN_GROUP_REQ = 5;
    /**
     * 进入群组响应
     */
    int JOIN_GROUP_RESP = 6;

    /**
     * 单聊消息请求
     */
    int SINGLE_MSG_REQ = 7;
    /**
     * 单聊消息响应
     */
    int SINGLE_MSG_RESP = 8;

    /**
     * 群聊消息请求
     */
    int GROUP_MSG_REQ = 9;
    /**
     * 群聊消息响应
     */
    int GROUP_MSG_RESP = 10;

    /**
     * 添加好友请求
     */
    int ADD_FRIEND_REQ = 11;

    /**
     * 添加好友响应
     */
    int ADD_FRIEND_RESP = 12;

    // ---------------------------------------------------------------

    /**
     * 单聊视频请求
     */
    int SINGLE_VIDEO_CHAT_REQ = 13;

    /**
     * 单聊视频响应
     */
    int SINGLE_VIDEO_CHAT_RESP = 13;


    //-----------------------------------------------------------------

    /**
     * 心跳
     */
    int HEART_BEAT_REQ = 99;

    /**
     * ACK
     */
    int ACK_REQ = 100;

    /**
     * 其他
     */
    int OTHER_REQ = 101;
}
