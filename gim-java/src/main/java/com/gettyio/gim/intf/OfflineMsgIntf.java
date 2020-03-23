package com.gettyio.gim.intf;

/**
 * 〈离线消息处理〉
 *
 * @author gogym
 * @version 2019年7月12日
 * @see OfflineMsgIntf
 * @since
 */
public abstract class OfflineMsgIntf {

    /**
     * Description: 获取要持久化的离线消息
     *
     * @param msg
     * @see
     */
    public abstract void offlineMsg(String msg);

}
