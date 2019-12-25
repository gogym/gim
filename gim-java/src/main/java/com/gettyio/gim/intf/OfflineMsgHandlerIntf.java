/*
 * 文件名：OfflineMsgHandlerIntf.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.intf;


import com.gettyio.gim.packet.MessageClass;

/**
 *
 * 〈离线消息处理〉
 *
 * @author gogym
 * @version 2019年7月12日
 * @see ClusterMsgHandlerIntf
 * @since
 */
public abstract class OfflineMsgHandlerIntf {

	/**
	 * 
	 * Description: 获取要持久化的离线消息
	 * 
	 * @param msg
	 * @see
	 */
	public abstract void offlineMsg(MessageClass.Message msg);

}
