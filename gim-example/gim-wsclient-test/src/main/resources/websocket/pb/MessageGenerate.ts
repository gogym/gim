import snowflake from '../../utils/snowflake'
import {ACK_REQ, HEART_BEAT_REQ, BIND_REQ, SINGLE_MSG_REQ} from '../reqType'
import {Message} from "./Message_pb";

export default class MessageGenerate {
    static instance: MessageGenerate

    snowflake: snowflake

    constructor() {
        this.snowflake = new snowflake(1n, 1n, 0n)
    }

    //单例模式
    static getInstance() {

        if (!MessageGenerate.instance) {
            MessageGenerate.instance = new MessageGenerate()
        }
        return MessageGenerate.instance
    }

    //构造消息
    CreateMessageBuilder(reqType: number) {
        let message = new Message()
        message.id = this.snowflake.nextId() + ''
        message.reqType = reqType
        message.msgTime = new Date().getTime()
        return message
    }

    //ack消息
    createAck(ack: string) {
        let builder = this.CreateMessageBuilder(ACK_REQ)
        // 创建一个ack
        builder.ack = ack
        // 把ack消息放到消息body里
        return builder
    }

    //心跳消息
    createHeartBeat() {
        let builder = this.CreateMessageBuilder(HEART_BEAT_REQ)
        return builder
    }

    /**
     * 创建绑定请求信息
     *
     * @param fromId
     * @return
     */
    createBindReq(fromId: string) {
        let builder = this.CreateMessageBuilder(BIND_REQ)
        builder.fromId = fromId
        return builder
    }

    //构建单聊消息
    createSingleMsgReq(fromId: string, toId: string, body: string) {

        let builder = this.CreateMessageBuilder(SINGLE_MSG_REQ)
        builder.fromId = fromId
        builder.toId = toId
        builder.body = body
        return builder
    }

}
