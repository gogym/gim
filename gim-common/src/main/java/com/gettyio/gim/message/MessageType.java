/*
 * Copyright 2019 The Getty Project
 *
 * The Getty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.gettyio.gim.message;


/**
 * MessageContentType.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public enum MessageType {

    /**
     * 单聊消息，群消息，单聊视频信令，添加好友请求,添加好友响应
     */
    SINGLE_MSG(1), GROUP_MSG(2), SINGLE_VIDEO_CHAT_SIGNALLING(3), ADD_FRIEND_REQ(4), ADD_FRIEND_RESP(5);

    private int value;

    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


    /**
     * 根据value返回枚举类型
     *
     * @param value
     * @return
     */
    public static MessageType getByValue(int value) {
        for (MessageType messageContentType : values()) {
            if (messageContentType.getValue() == value) {
                return messageContentType;
            }
        }
        return null;
    }

}
