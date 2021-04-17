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

    public static final String serverKey = "gim_";
    private final String redisKey = "gim_client_";
    private final String groupKey = "gim_group_";

    private GimContext gimContext;
    private GimConfig gimConfig;
    private IRedisProxy redisProxy;

    public ClusterRoute(GimContext gimContext) {
        this.gimContext = gimContext;
        this.gimConfig = gimContext.getGimConfig();
        this.redisProxy = RedisProxyImp.getInstance(gimConfig.getJedisPool());
    }

    /**
     * Description: 设置连接与服务器的路由映射
     *
     * @param id
     * @see
     */
    public void setRoute(String id) {
        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由，30分钟有效期，所以心跳来临需要刷新，保证连接的有效性
            redisProxy.setex(redisKey + id, 30 * 60, gimConfig.getServerId());
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
        return redisProxy.get(redisKey + id);
    }

    /**
     * Description: 删除用户路由
     *
     * @param id
     * @see
     */
    public void delRoute(String id) {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由
            redisProxy.del(redisKey + id);
        }

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
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.sadd(groupKey + groupId, id);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }

    }

    /**
     * Description: 用户绑定群组
     *
     * @param groupId
     * @param ids
     * @return
     * @see
     */
    public void setGroupRoute(String groupId, List<String> ids)
            throws Exception {

        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.sadd(groupKey + groupId, ids.toArray(new String[ids.size()]));
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
     * @param id
     * @throws Exception
     * @see
     */
    public void delGroupRoute(String groupId, String id) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.srem(groupKey + groupId, id);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }

    }

    public void delGroupRoute(String groupId, List<String> ids) throws Exception {

        // 先判断是否开启集群
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.srem(groupKey + groupId, ids.toArray(new String[ids.size()]));
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
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
        if (gimConfig.isEnableCluster()) {
            Long result = redisProxy.batchSrem(groupKey + groupId);
            if (null == result) {
                throw new Exception("setGroupRoute error");
            }
        }
    }

    /**
     * 清空所有群集合理由
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
    public void sendToCluster(MessageClass.Message msg, String serverId,String toId) throws Exception {
        //发送到集群的消息，加上发出服务器的标记，用于标记消息来自哪个服务器
        MessageClass.Message.Builder builder = msg.toBuilder().setServerId(gimConfig.getServerId()).setToId(toId);
        String msgJson = JsonFormat.printer().print(builder);
        //消息发到对应服务器的消息分组中，便于目标服务器读取
        redisProxy.lpush(serverKey + serverId, msgJson);
    }

}
