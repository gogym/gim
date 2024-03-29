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
package com.gettyio.gim.bind;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.gim.server.GimContext;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * GimBind.java
 *
 * @description:绑定类
 * @author:gogym
 * @date:2020/4/9
 * @copyright: Copyright by gettyio.com
 */
public class GimBind {

    private final GimContext gimContext;

    public GimBind(GimContext gimContext) {
        this.gimContext = gimContext;
    }

    /**
     * Description: 用户与连接绑定
     *
     * @param id
     * @param channel
     * @see
     */
    public void bind(String id, SocketChannel channel) {
        gimContext.getUserChannelMap().put(id, channel.getChannelId());

        if (gimContext.getGimConfig().isEnableCluster()) {
            //如果开启了集群，设置集群路由
            gimContext.getClusterRoute().setRoute(id);
        }
    }

    /**
     * Description: 解绑用户
     *
     * @param id
     * @see
     */
    public void unbind(String id) {
        gimContext.getUserChannelMap().remove(id);
        if (gimContext.getGimConfig().isEnableCluster()) {
            //如果开启了集群，清理集群路由
            gimContext.getClusterRoute().delRoute(id);
        }
    }


    /**
     * 解绑用户
     *
     * @param channelId
     */
    public void unbindByChannelId(String channelId) {

        if (gimContext.getGimConfig().isEnableCluster()) {
            for (String key : gimContext.getUserChannelMap().keySet()) {
                if (gimContext.getUserChannelMap().get(key).equals(channelId)) {
                    gimContext.getClusterRoute().delRoute(key);
                }
            }
        }

        Collection<String> col = gimContext.getUserChannelMap().values();
        while (col.contains(channelId)) {
            col.remove(channelId);
        }

    }


    /**
     * 绑定群组
     *
     * @param id
     * @param groupId
     * @return void
     */
    public void bindGroup(String groupId, String id) throws Exception {
        if (!gimContext.getGimConfig().isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.getGroupUserMap().get(groupId);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
                list.add(id);
                gimContext.getGroupUserMap().put(groupId, list);
            } else {
                list.add(id);
            }
        } else {
            //集群，添加到redis
            gimContext.getClusterRoute().setGroupRoute(groupId, id);
        }
    }

    /**
     * 绑定群组
     *
     * @param groupId
     * @param ids
     * @throws Exception
     */
    public void bindGroup(String groupId, List<String> ids) throws Exception {

        if (!gimContext.getGimConfig().isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.getGroupUserMap().get(groupId);
            if (list == null) {
                list = new CopyOnWriteArrayList<>(ids);
                gimContext.getGroupUserMap().put(groupId, list);
            } else {
                list.addAll(ids);
            }
        } else {
            //集群形式，添加到redis
            gimContext.getClusterRoute().setGroupRoute(groupId, ids);
        }
    }


    /**
     * 移除群绑定
     *
     * @param groupId
     * @param id
     * @return void
     */
    public void unbindGroup(String groupId, String id) throws Exception {

        if (!gimContext.getGimConfig().isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.getGroupUserMap().get(groupId);
            if (list == null) {
                return;
            }
            //移除
            list.remove(id);
        } else {
            gimContext.getClusterRoute().delGroupRoute(groupId, id);
        }
    }

    /**
     * 移除群绑定
     *
     * @param groupId
     * @param ids
     * @throws Exception
     */
    public void unbindGroup(String groupId, List<String> ids) throws Exception {

        if (!gimContext.getGimConfig().isEnableCluster()) {
            //非集群
            CopyOnWriteArrayList<String> list = gimContext.getGroupUserMap().get(groupId);
            if (list == null) {
                return;
            }
            //移除
            list.removeAll(ids);
        } else {
            gimContext.getClusterRoute().delGroupRoute(groupId, ids);
        }
    }

    /**
     * 清理群组
     *
     * @param groupId
     * @return void
     */
    public void clearGroup(String groupId) throws Exception {
        if (!gimContext.getGimConfig().isEnableCluster()) {
            gimContext.getGroupUserMap().remove(groupId);
        } else {
            gimContext.getClusterRoute().clearGroupRoute(groupId);
        }
    }


    /**
     * 清理全部群组
     *
     * @throws Exception
     */
    public void clearGroupAll() throws Exception {

        if (!gimContext.getGimConfig().isEnableCluster()) {
            gimContext.getGroupUserMap().clear();
        } else {
            gimContext.getClusterRoute().clearAllGroupRoute();
        }

    }

}
