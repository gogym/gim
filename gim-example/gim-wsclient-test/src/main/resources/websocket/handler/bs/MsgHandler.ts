import AbsMessageHandler from '../AbsMessageHandler'
import {Message} from "@/websocket/pb/Message_pb";
import MessageBody from '../../../../db/model/messageBody';
import {plainToClass} from 'class-transformer'
import store from '../../../store'

export default class MsgHandler extends AbsMessageHandler {

    //开始处理信息
    handler(messagePb: Message) {
        //json转对象
        let messageBody = plainToClass(MessageBody, JSON.parse(messagePb.body));
        //消息处理
        return store.dispatch("message/handlerMsg", messageBody)
    }
}
