package com.gettyio.gim.client.core;


/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName OnConnectLintener.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/24/ 17:42:00
 */
public interface OnConnectLintener {

    void onCompleted(GimContext gimContext);

    void onFailed(Throwable throwable);
}
