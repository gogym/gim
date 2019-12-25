/*
 * 文件名：ChatBind.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.bind;

import com.gettyio.core.channel.AioChannel;
import com.gettyio.gim.server.GimContext;

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

    }

    /**
     * Description: 解绑用户
     *
     * @param userId
     * @see
     */
    public void unbindUser(String userId) {
        gimContext.userChannelMap.remove(userId);
    }


}
