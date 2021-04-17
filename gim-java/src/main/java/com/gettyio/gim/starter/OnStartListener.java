package com.gettyio.gim.starter;

import com.gettyio.gim.server.GimContext;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName OnStartListener.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/09/ 17:23:00
 */
public interface OnStartListener {

    /**
     * 启动回调
     * @param gimContext
     */
    void onStart(GimContext gimContext);
}
