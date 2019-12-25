/*
 * 文件名：PacketConfig.java
 * 版权：Copyright by www.poly.com
 * 描述：
 * 修改人：gogym
 * 修改时间：2019年7月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gettyio.gim.server;

import java.util.ArrayList;
import java.util.List;


import com.gettyio.gim.packet.AckReqClass;
import com.gettyio.gim.packet.GroupChatReqClass;
import com.gettyio.gim.packet.SingleChatReqClass;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.util.JsonFormat;

public class PacketConfig {

    private List<Descriptor> list = new ArrayList<Descriptor>();

    // private static PacketConfig packetConfig = new PacketConfig();
    //
    // public static PacketConfig shareInstance() {
    // return packetConfig;
    // }

    public PacketConfig() {
        this.list.add(AckReqClass.AckReq.getDescriptor());
        this.list.add(SingleChatReqClass.SingleChatReq.getDescriptor());
        this.list.add(GroupChatReqClass.GroupChatReq.getDescriptor());
    }

    public PacketConfig addType(Descriptor messageType) {

        if (messageType == null) {
            throw new IllegalStateException(
                    "A TypeRegistry.Builer can only be used once.");
        }

        this.list.add(messageType);
        return this;
    }

    public PacketConfig addTypes(List<Descriptor> messageTypes) {

        if (messageTypes == null) {
            throw new IllegalStateException(
                    "A TypeRegistry.Builer can only be used once.");
        }
        this.list.addAll(messageTypes);
        return this;
    }

    public JsonFormat.TypeRegistry getTypeRegistry() {

        JsonFormat.TypeRegistry.Builder builer = JsonFormat.TypeRegistry
                .newBuilder().add(list);
        return builer.build();
    }

}
