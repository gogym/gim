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
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
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

    private final String redisKey = "gim_client_";
    private final String groupKey = "gim_group_";

    private GimContext gimContext;
    private GimConfig gimConfig;
    private IRedisProxy redisProxy;

    public ClusterRoute(GimContext gimContext) {
        this.gimContext = gimContext;
        this.gimConfig = gimContext.gimConfig;
        this.redisProxy = RedisProxyImp.getInstance(gimConfig.getJedisPool());
    }

    /**
     * Description: 设置用户与服务器的路由映射
     *
     * @param userId
     * @see
     */
    public void setUserRoute(String userId) {
        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由，30分钟有效期，所以心跳来临需要刷新，保证连接的有效性
            redisProxy.setex(redisKey + userId, 30 * 60, gimConfig.getServerId());
        }
    }

    /**
     * Description: 获取用户与服务器的路由映射
     *
     * @param userId
     * @return
     * @see
     */
    public String getUserRoute(String userId) {
        return redisProxy.get(redisKey + userId);
    }

    /**
     * Description: 删除用户路由
     *
     * @param userId
     * @see
     */
    public void delUserRoute(String userId) {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由
            redisProxy.del(redisKey + userId);
        }

    }

    /**
     * Description: 用户绑定群组
     *
     * @param groupId
     * @param userId
     * @return
     * @see
     */
    public void setGroupRoute(String groupId, String userId) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.sadd(groupKey + groupId, userId);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }

    }

    /**
     * Description: 用户绑定群组
     *
     * @param groupId
     * @param userIds
     * @return
     * @see
     */
    public void setGroupRoute(String groupId, List<String> userIds)
            throws Exception {

        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.sadd(groupKey + groupId, userIds.toArray(new String[userIds.size()]));
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
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
        if (gimConfig.isEnableCluster()) {
            Set<String> result = redisProxy.smembers(groupKey + groupId);
            return result;
        }
        return null;
    }

    /**
     * Description: 删除群路由
     *
     * @param groupId
     * @param userId
     * @throws Exception
     * @see
     */
    public void delGroupRoute(String groupId, String userId) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.srem(groupKey + groupId, userId);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }

    }

    public void delGroupRoute(String groupId, List<String> userIds) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.srem(groupKey + groupId, userIds.toArray(new String[userIds.size()]));
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
    }

    /**
     * Description: 清空集合
     *
     * @param groupId
     * @throws Exception
     * @see
     */
    public void clearGroupRoute(String groupId) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.batchSrem(groupKey + groupId);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
    }

    /**
     * 清空所有集合
     *
     * @throws Exception
     */
    public void clearAllGroupRoute() throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.batchSrem(groupKey + "*");
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
    }


    /**
     * 向集群发送消息
     *
     * @param msg
     * @see
     */
    public void sendToCluster(MessageClass.Message msg, String serverId) throws Exception {
        //发送到集群的消息，加上发出服务器的标记，用于标记消息来自哪个服务器
        MessageClass.Message.Builder builder = msg.toBuilder().setServerId(gimConfig.getServerId());
        String msgJson = JsonFormat.printer().print(builder);
        redisProxy.lpush("gim_" + serverId, msgJson);
    }

}
