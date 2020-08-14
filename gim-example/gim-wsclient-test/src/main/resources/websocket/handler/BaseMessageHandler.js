import messagePb from '../pb/Message_pb'
import AbsMessageHandler from './AbsMessageHandler'

export default class BaseMessageHandler {

    handlerMap

    constructor (handlerMap) {
        this.handlerMap = handlerMap
    }

    read (messagePb) {
        //根据类型获取对应的处理器
        let type = messagePb.getReqtype()
        let abs = this.handlerMap.get(type)
        if (abs) {
            abs.handler(messagePb)
        }
    }

}
