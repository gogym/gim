package com.gettyio.gim.client.message;

import com.gettyio.gim.client.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.TypeRegistry;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 包装消息
 * 
 * @author LONG
 */
public class MessageDelayPacket implements Delayed {

	private long delay; // 延迟时间
	private long expire; // 到期时间
	private long now; // 创建时间

	private MessageClass.Message message;

	private String userId;
	private String serverIdentify;

	public MessageDelayPacket(String userId, MessageClass.Message message, long delay) {
		this.userId = userId;
		this.message = message;
		this.delay = delay;
		this.expire = System.currentTimeMillis() + delay; // 到期时间 = 当前时间+延迟时间
		this.now = System.currentTimeMillis();
	}

	public MessageDelayPacket(String serverIdentify, String userId,
							  MessageClass.Message message, long delay) {
		this.serverIdentify = serverIdentify;
		this.userId = userId;
		this.message = message;
		this.delay = delay;
		this.expire = System.currentTimeMillis() + delay; // 到期时间 = 当前时间+延迟时间
		this.now = System.currentTimeMillis();
	}

	public String msgToJson(TypeRegistry typeRegistry) {

		try {
			String msgJson = JsonFormat
					.printer()
					.usingTypeRegistry(typeRegistry)
					.print(message.toBuilder()
							.setServerIdentify(serverIdentify));
			return msgJson;
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
	}

	public MessageClass.Message getMessage() {
		return message;
	}

	public void setMessage(MessageClass.Message message) {
		this.message = message;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
		this.now = System.currentTimeMillis();
		this.expire = now + delay; // 到期时间 = 当前时间+延迟时间
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getServerIdentify() {

		return serverIdentify;
	}

	public void setServerIdentify(String serverIdentify) {

		this.serverIdentify = serverIdentify;
	}

	@Override
	public int compareTo(Delayed o) {
		if (this.getDelay(TimeUnit.MILLISECONDS) > o
				.getDelay(TimeUnit.MILLISECONDS)) {
			return 1;
		} else if (this.getDelay(TimeUnit.MILLISECONDS) < o
				.getDelay(TimeUnit.MILLISECONDS)) {
			return -1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return this.expire - System.currentTimeMillis();
	}

}
