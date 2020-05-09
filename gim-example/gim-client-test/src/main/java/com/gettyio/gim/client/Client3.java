package com.gettyio.gim.client;/*
 * 类名：ClientStarter
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2019/12/25
 */


import com.gettyio.gim.comm.Const;
import com.gettyio.gim.packet.MessageInfo;
import com.gettyio.gim.utils.FastJsonUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.ArrayList;
import java.util.List;

public class Client3 {

    public static void main(String[] args) {

        MessageClass.Message.Builder builder = MessageClass.Message.newBuilder();
        builder.setVersion(Const.VERSION);
        byte[] s1 = "aaa".getBytes();
        //bytes 类型
        builder.setField4(ByteString.copyFrom(s1));
        //repeated string 类型
        builder.addField5("bbb");
        builder.addField5("ccc");

        MessageClass.Message message = builder.build();
        System.out.println(message.getField4().toStringUtf8());
        //要用List，因为ProtocolStringList的继承的是list
        List<String> list = message.getField5List();
        for (String s : list) {
            System.out.println("list:" + s);
        }
        //如果要转成ArrayList，可以这样
        ArrayList<String> arrayList = new ArrayList<>(list);

        try {
            //先转成json，解决不同数据协议之间的桎梏
            String msgJson = JsonFormat.printer().print(message.toBuilder());
            System.out.println("msgJson:" + msgJson);
            //通过fastJson转成指定的javabean
            MessageInfo messageInfo = FastJsonUtils.toBean(msgJson, MessageInfo.class);
            //这个是byte[]
           // System.out.println("messageInfo:" + new String(messageInfo.getField4()));
            //这个是list
            //System.out.println("messageInfo:" + messageInfo.getField5().get(0));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }


}
