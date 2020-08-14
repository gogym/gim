import AbsMessageHandler from '../AbsMessageHandler'

const MessageTitle = window.messageTitle.messageTitle
const dbHelper = window.db_helpder.db_helpder

export default class MsgHandler extends AbsMessageHandler {

    handler (messagePb) {
        let msg = new MessageTitle()
        msg.setId('123')
        dbHelper.getInstance().addMessageTitle(msg)

    }
}
