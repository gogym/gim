import AbsMessageHandler from '../AbsMessageHandler'

export default class BindHandler extends AbsMessageHandler {

    //重写父类消息处理
    handler (messagePb) {
        console.log('绑定信息')
    }

}
