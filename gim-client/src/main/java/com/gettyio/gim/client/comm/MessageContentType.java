package com.gettyio.gim.client.comm;

/**
 * 消息体内容类型
 *
 * @author：gj
 * @date: 2017/3/21
 * @time: 14:41
 **/
public enum MessageContentType {

    // 文字，图片，音频，视频
    text(1), image(2), audio(3), video(4);

    private int value;

    // 构造器默认只能是private, 从而保证构造函数只能在内部使用
    private MessageContentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    // 更加value返回枚举类型
    public static MessageContentType getByValue(int value) {
        for (MessageContentType messageContentType : values()) {
            if (messageContentType.getValue() == value) {
                return messageContentType;
            }
        }
        return null;
    }

}
