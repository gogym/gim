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
import com.google.protobuf.GeneratedMessageV3;


/**
 * AbsChatHandler.java
 *
 * @description:消息分发基类
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public abstract class AbsChatHandler<T extends GeneratedMessageV3> implements AbsHandlerIntf {


    /**
     * 获取子类类型
     *
     * @return
     */
    public abstract Class<T> bodyClass();

    @Override
    public void handler(MessageClass.Message message, SocketChannel socketChannel) throws Exception {
        // 根据类型转换消息
        T t = bodyClass().cast(message);
        handler(t, socketChannel);
    }


    /**
     * 把消息分发给指定的业务处理器
     *
     * @param message
     * @param socketChannel
     * @throws Exception
     */
    public abstract void handler(T message, SocketChannel socketChannel) throws Exception;

}
