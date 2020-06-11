package com.gettyio.gim.utils;/*
 * 类名：MessageUtil
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/6/10
 */

import com.gettyio.gim.packet.MessageClass;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

public class MessageUtil {

    /**
     * @param message
     * @return
     */
    public static String toJson(MessageClass.Message message) {
        try {
            String msgJson = JsonFormat.printer().print(message.toBuilder());
            return msgJson;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }
}
