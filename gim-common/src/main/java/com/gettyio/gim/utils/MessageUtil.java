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
package com.gettyio.gim.utils;

import com.gettyio.gim.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

/**
 * 消息工具类
 * 类名：MessageUtil
 * 版权：Copyright by www.getty.com
 * 时间：2020/6/10
 *
 * @author gogym
 */
public class MessageUtil {

    /**
     * 消息转换为json
     *
     * @param message
     * @return
     */
    public static String toJson(MessageClass.Message message) {
        try {
            String msgJson = JsonFormat.printer().print(message.toBuilder());
            return msgJson;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }
}
