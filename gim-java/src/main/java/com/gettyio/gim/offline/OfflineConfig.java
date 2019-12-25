/*
 * 文件名：OfflineConfig.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.offline;

import com.gettyio.gim.intf.OfflineMsgHandlerIntf;
import com.gettyio.gim.packet.MessageClass;

import java.util.concurrent.BlockingQueue;


public class OfflineConfig {

	public static int QUEUE = 1;
	public static int HANDLER = 2;

	private boolean isOffline = false;

	private int handleType = QUEUE;

	// 创建一个阻塞队列，用于缓冲需要持久化的离线消息
	private BlockingQueue<MessageClass.Message> offlineMsgQueue;

	private OfflineMsgHandlerIntf OfflineMsgHandler;

	public OfflineConfig isOffline(boolean isOffline) {
		this.isOffline = isOffline;
		return this;
	}

	public OfflineConfig handleType(int handleType) {
		this.handleType = handleType;
		return this;
	}

	public OfflineConfig offlineMsgQueue(BlockingQueue<MessageClass.Message> offlineMsgQueue) {
		this.offlineMsgQueue = offlineMsgQueue;
		return this;
	}

	public OfflineConfig offlineMsgHandler(
			OfflineMsgHandlerIntf OfflineMsgHandler) {
		this.OfflineMsgHandler = OfflineMsgHandler;
		return this;
	}

	public boolean isOffline() {

		return isOffline;
	}

	public int getHandleType() {

		return handleType;
	}

	public BlockingQueue<MessageClass.Message> getOfflineMsgQueue() {

		return offlineMsgQueue;
	}

	public OfflineMsgHandlerIntf getOfflineMsgHandler() {

		return OfflineMsgHandler;
	}

}
