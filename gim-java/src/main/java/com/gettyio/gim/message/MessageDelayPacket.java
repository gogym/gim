package com.gettyio.gim.message;

import com.gettyio.gim.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 包装消息
 *
 * @author gogym
 */
public class MessageDelayPacket implements Delayed {

    private long originalDelay; // 延迟时间
    private long expire; // 到期时间
    private long now; // 创建时间

    //投递次数，初始1
    private Integer num = 1;

    private MessageClass.Message message;

    public MessageDelayPacket(MessageClass.Message message, long delay) {
        this.message = message;
        this.originalDelay = delay;
        this.expire = System.currentTimeMillis() + delay; // 到期时间 = 当前时间+延迟时间
        this.now = System.currentTimeMillis();
    }


    public String toJson() {
        try {
            String msgJson = JsonFormat.printer().print(message.toBuilder());
            return msgJson;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getNum() {
        return num;
    }

    public Integer incrNum() {
        num++;
        return num;
    }

    public MessageClass.Message getMessage() {
        return message;
    }

    public void setMessage(MessageClass.Message message) {
        this.message = message;
    }


    /**
     * 获取初始的延迟时间
     *
     * @return long
     * @params []
     */
    public long getOriginalDelay() {
        return originalDelay;
    }

    public void setDelay(long delay) {
        this.originalDelay = delay;
        this.now = System.currentTimeMillis();
        this.expire = now + delay; // 到期时间 = 当前时间+延迟时间
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.expire - System.currentTimeMillis();
    }

}
