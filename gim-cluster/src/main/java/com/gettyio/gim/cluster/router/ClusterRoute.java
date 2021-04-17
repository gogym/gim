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
package com.gettyio.gim.cluster.router;

import com.gettyio.gim.cluster.config.ClusterConfig;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.redis.IRedisProxy;
import com.gettyio.gim.redis.RedisProxyImp;
import com.google.protobuf.util.JsonFormat;

import java.util.List;
import java.util.Set;

/**
 * ClusterRoute.java
 *
 * @description:集群代理
 * @author:gogym
 * @date:2020/4/9
 * @copyright: Copyright by gettyio.com
 */
public class ClusterRoute {


    private ClusterConfig clusterConfig;

    public ClusterRoute(ClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    /**
     * Description: 设置连接与服务器的路由映射
     *
     * @param id
     * @see
     */
    public void setRoute(String id) {
        // 先判断是否开启集群
        if (clusterConfig.isEnableCluster()) {
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由，30分钟有效期，所以心跳来临需要刷新，保证连接的有效性

        }
    }

    /**
     * Description: 获取用户与服务器的路由映射
     *
     * @param id
     * @return
     * @see
     */
    public String getRoute(String id) {
        return null;
    }

    /**
     * Description: 删除用户路由
     *
     * @param id
     * @see
     */
    public void delRoute(String id) {

        // 先判断是否开启集群


    }

    /**
     * Description: 用户绑定群组
     *
     * @param groupId
     * @param id
     * @return
     * @see
     */
    public void setGroupRoute(String groupId, String id) throws Exception {

        // 先判断是否开启集群


    }

    /**
     * Description: 用户绑定群组
     *
     * @param groupId
     * @param ids
     * @return
     * @see
     */
    public void setGroupRoute(String groupId, List<String> ids) throws Exception {


    }

    /**
     * Description: 获取群组路由
     *
     * @param groupId
     * @return
     * @see
     */
    public Set<String> getGroupRoute(String groupId) {
        // 先判断是否开启集群

        return null;
    }

    /**
     * Description: 删除群路由
     *
     * @param groupId
     * @param id
     * @throws Exception
     * @see
     */
    public void delGroupRoute(String groupId, String id) throws Exception {

        // 先判断是否开启集群


    }

    public void delGroupRoute(String groupId, List<String> ids) throws Exception {

        // 先判断是否开启集群

    }

    /**
     * Description: 清空群集合路由
     *
     * @param groupId
     * @throws Exception
     * @see
     */
    public void clearGroupRoute(String groupId) throws Exception {

        // 先判断是否开启集群

    }

    /**
     * 清空所有群集合理由
     *
     * @throws Exception
     */
    public void clearAllGroupRoute() throws Exception {

        // 先判断是否开启集群

    }


    /**
     * 向集群发送消息
     *
     * @param msg
     * @see
     */
    public void sendToCluster(MessageClass.Message msg, String serverId) throws Exception {
        //发送到集群的消息，加上发出服务器的标记，用于标记消息来自哪个服务器
        MessageClass.Message.Builder builder = msg.toBuilder().setServerId(clusterConfig.getServerId());
        String msgJson = JsonFormat.printer().print(builder);

    }

}
