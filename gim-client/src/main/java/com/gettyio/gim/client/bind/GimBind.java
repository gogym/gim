package com.gettyio.gim.client.bind;

import com.gettyio.gim.client.core.GimContext;

/**
 * 类名：GimBind.java
 * 描述：绑定工具类
 * 修改人：gogym
 * 时间：2020/3/18
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
     * @see
     */
    public void bindUser(String userId) {
        gimContext.messagEmitter.sendConnectReq(userId);
    }


}
