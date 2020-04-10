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
package com.gettyio.gim.client.handler;


import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.packet.MessageClass;

import java.util.Map;

/**
 * BaseChatHandler.java
 *
 * @description:业务处理器基类,用于业务分发
 * @author:gogym
 * @date:2020/4/9
 * @copyright: Copyright by gettyio.com
 */
public class BaseChatHandler implements ChatListener {

    Map<Integer, AbsChatHandler<?>> handlerMap;

    public BaseChatHandler(Map<Integer, AbsChatHandler<?>> handlerMap) {
        this.handlerMap = handlerMap;
    }


    @Override
    public void read(MessageClass.Message message, SocketChannel socketChannel) throws Exception {

        //根据消息查找对应的处理器
        Integer type = message.getReqType();
        AbsChatHandler<?> absChatHandler = handlerMap.get(type);
        if (absChatHandler == null) {
            throw new Exception("找不到对应消息处理器");
        }

        absChatHandler.handler(message, socketChannel);
    }
}
