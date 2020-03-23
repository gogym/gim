package com.gettyio.gim.cluster;

import com.gettyio.gim.cluster.redis.IRedisProxy;
import com.gettyio.gim.cluster.redis.RedisProxyImp;
import com.gettyio.gim.packet.MessageClass;
import com.gettyio.gim.server.GimConfig;
import com.gettyio.gim.server.GimContext;
import com.google.protobuf.util.JsonFormat;

import java.util.List;
import java.util.Set;


public class ClusterRoute {

    private final String redisKey = "gim_client_";
    private final String groupKey = "gim_group_";

    private GimContext gimContext;
    private IRedisProxy redisProxy;
    private GimConfig gimConfig;

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
            // 把用户连接对应所在服务器标志写到redis,便于分布式路由，30分钟有效期
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
     * 向集群发送消息
     *
     * @param msg
     * @see
     */
    public void sendToCluster(MessageClass.Message msg, String serverId) throws Exception {
        //发送到集群的消息，加上发出服务器的标记
        MessageClass.Message.Builder builder = msg.toBuilder().setServerId(gimConfig.getServerId());
        String msgJson = JsonFormat.printer().print(builder);
        redisProxy.lpush("gim_" + serverId, msgJson);
    }

}
