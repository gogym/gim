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
package com.gettyio.gim.client.expansion;

import com.gettyio.core.util.timer.HashedWheelTimer;
import com.gettyio.core.util.timer.Timeout;
import com.gettyio.core.util.timer.TimerTask;
import com.gettyio.gim.client.core.GimContext;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;

import java.util.concurrent.TimeUnit;


/**
 * HeartBeatHandler.java
 *
 * @description:心跳
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class HeartBeatHandler implements TimerTask {


    GimContext gimContext;
    /**
     * 创建一个定时器
     */
    private final HashedWheelTimer timer;

    public HeartBeatHandler(GimContext gimContext) {
        this.gimContext = gimContext;
        this.timer = new HashedWheelTimer();
    }


    public void start() {
        timer.newTimeout(this, gimContext.gimConfig.getHeartBeatInterval(), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void run(Timeout timeout) throws Exception {

        MessageClass.Message msg = MessageGenerate.getInstance().createHeartBeat();
        gimContext.messagEmitter.sendOnly(msg);
        //重复调用，维持心跳
        if (!gimContext.socketChannel.isInvalid()) {
            timer.newTimeout(this, gimContext.gimConfig.getHeartBeatInterval(), TimeUnit.MILLISECONDS);
        }
    }
}
