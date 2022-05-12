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
public interface ReqType {

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
     * 单聊消息请求
     */
    int SINGLE_MSG_REQ = 5;
    /**
     * 单聊消息响应
     */
    int SINGLE_MSG_RESP = 6;

    /**
     * 群聊消息请求
     */
    int GROUP_MSG_REQ = 7;
    /**
     * 群聊消息响应
     */
    int GROUP_MSG_RESP = 8;


    //-----------------------------------------------------------------

    /**
     * 心跳
     */
    int HEART_BEAT_REQ = 99;

    /**
     * ACK
     */
    int ACK_REQ = 100;
}
