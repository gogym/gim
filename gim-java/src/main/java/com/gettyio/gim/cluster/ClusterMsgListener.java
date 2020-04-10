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
package com.gettyio.gim.cluster;

import com.gettyio.gim.redis.IRedisProxy;
import com.gettyio.gim.redis.RedisProxyImp;
import com.gettyio.gim.comm.Type;
import com.gettyio.gim.message.MessageGenerate;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

import java.util.List;


/**
 * ClusterMsgListener.java
 *
 * @description:集群消息监听
 * @author:gogym
 * @date:2020/4/9
 * @copyright: Copyright by gettyio.com
 */
public class ClusterMsgListener implements Runnable {
    /**
     * 服务器标识
     */
    private String serverId;
    private IRedisProxy redisProxy;
    private GimContext gimContext;

    public ClusterMsgListener(GimContext gimContext) {
        this.gimContext = gimContext;
        this.serverId = gimContext.gimConfig.getServerId();
        this.redisProxy = RedisProxyImp.getInstance(gimContext.gimConfig.getJedisPool());
    }


    @Override
    public void run() {
        try {
            while (true) {
                processMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 消息处理
     *
     * @param
     * @return void
     */
    private void processMessage() throws Exception {
        List<String> msgJson = redisProxy.brpop(0, "gim_" + serverId);
        if (msgJson != null && msgJson.size() != 0) {
            // 由于该指令可以监听多个Key,所以返回的是一个列表
            // 列表由2项组成，1) 列表名，2)数据
            String keyName = msgJson.get(0);
            // 如果返回的是MESSAGE_KEY的消息
            if (keyName.equals("gim_" + serverId)) {
                String message = msgJson.get(1);
                MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
                JsonFormat.parser().merge(message, builder);
                if (builder.getReqType() != Type.ACK_REQ) {
                    //需要创建一条ACK消息告诉集群对端服务器已经收到消息了
                    MessageClass.Message ack = MessageGenerate.getInstance(serverId).createAck(builder.getId());
                    gimContext.clusterRoute.sendToCluster(ack, builder.getServerId());
                }
                //业务处理
                gimContext.chatListener.read(builder.build(), null);
            }
        }
    }


}
