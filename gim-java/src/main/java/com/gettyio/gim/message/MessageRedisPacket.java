/*
 * 文件名：MessageRedisPacket.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年7月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.message;


import com.gettyio.gim.packet.MessageClass;

public class MessageRedisPacket {

	private String serverIdentify;
	private MessageClass.Message message;

	public MessageRedisPacket(String serverIdentify, MessageClass.Message message) {
		this.serverIdentify = serverIdentify;
		this.message = message;
	}

	public String getServerIdentify() {

		return serverIdentify;
	}

	public MessageClass.Message getMessage() {

		return message;
	}

}
