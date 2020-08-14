import messagePb from './Message_pb.js'
import Snowflake from '@/utils/Snowflake'
import { ACK_REQ, HEART_BEAT_REQ, BIND_REQ } from '../MsgType'

export default class MessageGenerate {

    snowflake

    constructor () {
        this.snowflake = new Snowflake(1n, 1n, 0n)
    }

    //单例模式
    static getInstance () {

        if (!MessageGenerate.instance) {
            MessageGenerate.instance = new MessageGenerate()
        }
        return MessageGenerate.instance
    }

    //构造消息
    CreateMessageBuilder (reqType) {
        let message = new messagePb.Message()
        message.setId(this.snowflake.nextId() + '')
        message.setReqtype(reqType)
        message.setMsgtime(new Date().getTime())
        this.snowflake.nextId()

        return message
    }

    //ack消息
    createAck (ack) {
        let builder = this.CreateMessageBuilder(ACK_REQ)
        // 创建一个ack
        builder.setAck(ack)
        // 把ack消息放到消息body里
        return builder
    }

    //心跳消息
    createHeartBeat () {
        let builder = this.CreateMessageBuilder(HEART_BEAT_REQ)
        return builder
    }

    /**
     * 创建绑定请求信息
     *
     * @param fromId
     * @return
     */
    createBindReq (fromId) {
        let builder = this.CreateMessageBuilder(BIND_REQ)
        builder.setFromid(fromId)
        return builder
    }

}
