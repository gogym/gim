import AbsMessageHandler from '../AbsMessageHandler'
import {Message} from "../../pb/Message_pb";

export default class BindHandler extends AbsMessageHandler {

    //重写父类消息处理
    handler(messagePb: Message) {
        console.log('绑定信息:'+JSON.stringify(messagePb.toJSON()))
    }

}
