import {Message} from '../pb/Message_pb'
import AbsMessageHandler from './AbsMessageHandler'

export default class BaseMessageHandler {

    handlerMap: Map<number, AbsMessageHandler>

    constructor(handlerMap: Map<number, AbsMessageHandler>) {
        this.handlerMap = handlerMap
    }

    read(messagePb: Message) {
        //根据类型获取对应的处理器
        let type = messagePb.reqType
        let abs = this.handlerMap.get(type)
        if (abs) {
            abs.handler(messagePb)
        }
    }

}
