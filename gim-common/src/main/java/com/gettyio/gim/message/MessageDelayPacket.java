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

import com.gettyio.gim.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * MessageDelayPacket.java
 *
 * @description:包装延迟消息
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class MessageDelayPacket implements Delayed {

    /**
     * 延迟时间
     */
    private long originalDelay;
    /**
     * 到期时间
     */
    private long expire;
    /**
     * 创建时间
     */
    private long now;

    /**
     * 投递次数，初始1
     */
    private Integer num = 1;

    /**
     * 消息体
     */
    private MessageClass.Message message;

    public MessageDelayPacket(MessageClass.Message message, long delay) {
        this.message = message;
        this.originalDelay = delay;
        // 到期时间 = 当前时间+延迟时间
        this.expire = System.currentTimeMillis() + delay;
        this.now = System.currentTimeMillis();
    }


    /**
     * 转成json
     *
     * @return
     */
    public String toJson() {
        try {
            String msgJson = JsonFormat.printer().print(message.toBuilder());
            return msgJson;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getNum() {
        return num;
    }

    /**
     * 投递次数递增1
     *
     * @return
     */
    public Integer incrNum() {
        num++;
        return num;
    }

    public MessageClass.Message getMessage() {
        return message;
    }

    public void setMessage(MessageClass.Message message) {
        this.message = message;
    }


    /**
     * 获取初始的延迟时间
     *
     * @return long
     * @params []
     */
    public long getOriginalDelay() {
        return originalDelay;
    }

    public void setDelay(long delay) {
        this.originalDelay = delay;
        this.now = System.currentTimeMillis();
        // 到期时间 = 当前时间+延迟时间
        this.expire = now + delay;
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.expire - System.currentTimeMillis();
    }

}
