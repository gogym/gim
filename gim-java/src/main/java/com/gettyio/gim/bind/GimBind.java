package com.gettyio.gim.bind;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.server.GimContext;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * 类名：GimBind.java
 * 描述：绑定类
 * 修改人：gogym
 * 时间：2020/3/23
 */
public class GimBind {

    private GimContext gimContext;

    public GimBind(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * Description: 用户与连接绑定
     *
     * @param userId
     * @param channel
     * @see
     */
    public void bindUser(String userId, AioChannel channel) {
        gimContext.userChannelMap.put(userId, channel.getChannelId());

        if (gimContext.gimConfig.isEnableCluster()) {
            //如果开启了集群，设置集群路由
            gimContext.clusterRoute.setUserRoute(userId);
        }
    }

    /**
     * Description: 解绑用户
     *
     * @param userId
     * @see
     */
    public void unbindUser(String userId) {
        gimContext.userChannelMap.remove(userId);
        if (gimContext.gimConfig.isEnableCluster()) {
            //如果开启了集群，设置集群路由
            gimContext.clusterRoute.delUserRoute(userId);
        }
    }


    public void unbindUserByChannelId(String channelId) {

        if (gimContext.gimConfig.isEnableCluster()) {
            for (String key : gimContext.userChannelMap.keySet()) {
                if (gimContext.userChannelMap.get(key).equals(channelId)) {
                    gimContext.clusterRoute.delUserRoute(key);
                }
            }
        }


        Collection<String> col = gimContext.userChannelMap.values();
        while (true == col.contains(channelId)) {
            col.remove(channelId);
        }


    }


    /**
     * 绑定群组
     *
     * @param userId
     * @param groupId
     * @return void
     */
    public void bindGroup(String groupId, String userId) throws Exception {
        if (!gimContext.gimConfig.isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
                list.add(userId);
                gimContext.groupUserMap.put(groupId, list);
            } else {
                list.add(userId);
            }
        } else {
            //集群形式，添加到redis
            gimContext.clusterRoute.setGroupRoute(groupId, userId);
        }
    }

    public void bindGroup(String groupId, List<String> users) throws Exception {

        if (!gimContext.gimConfig.isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
                list.addAll(users);
                gimContext.groupUserMap.put(groupId, list);
            } else {
                list.addAll(users);
            }
        } else {
            //集群形式，添加到redis
            gimContext.clusterRoute.setGroupRoute(groupId, users);
        }
    }


    /**
     * 移除群绑定
     *
     * @param groupId
     * @param userId
     * @return void
     */
    public void delGroup(String groupId, String userId) throws Exception {

        if (!gimContext.gimConfig.isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
            if (list == null) {
                return;
            }
            //移除
            list.remove(userId);
        } else {
            gimContext.clusterRoute.delGroupRoute(groupId, userId);
        }
    }


    public void delGroup(String groupId, List<String> users) throws Exception {

        if (!gimContext.gimConfig.isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.groupUserMap.get(groupId);
            if (list == null) {
                return;
            }
            //移除
            list.removeAll(users);
        } else {
            gimContext.clusterRoute.delGroupRoute(groupId, users);
        }
    }

    /**
     * 清理群组
     *
     * @param groupId
     * @return void
     */
    public void clearGroup(String groupId) throws Exception {
        if (!gimContext.gimConfig.isEnableCluster()) {
            gimContext.groupUserMap.remove(groupId);
        } else {
            gimContext.clusterRoute.clearGroupRoute(groupId);
        }
    }

}
