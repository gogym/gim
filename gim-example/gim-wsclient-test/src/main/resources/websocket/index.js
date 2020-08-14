import {
    WS_PROTOCOL,
    WS_IP,
    WS_PORT,
    HEART_BEAT_INTERVAL,
    RECONNECT_INTERVAL,
    BINTRAY_TYPE,
    NAMESPACE
} from './constant/index'
import vuexStore from '../store'
import messagePb from './pb/Message_pb.js'
import messageGenerate from './pb/MessageGenerate'

import { BIND_REQ, BIND_RESP, SINGLE_MSG_REQ } from './MsgType'
import BindHandler from './handler/bs/BindHandler'
import BaseMessageHandler from './handler/BaseMessageHandler'
import MsgHandler from './handler/bs/MsgHandler'

export default class VueWebSocket {
    //主动关闭标志，如果是主动关闭连接，则设置为true。
    userDisconnect = false
    isconnected = false
    messageGenerate
    baseMessageHandler

    //构造方法，实例化后会立即执行该方法
    constructor () {

        this.messageGenerate = messageGenerate.getInstance()
        //协议
        this.ws_protocol = WS_PROTOCOL
        //ip
        this.ip = WS_IP
        //端口
        this.port = WS_PORT
        //心跳间隔
        this.heartbeatTimeout = HEART_BEAT_INTERVAL
        //重连间隔
        this.reconnectInterval = RECONNECT_INTERVAL
        this.binaryType = BINTRAY_TYPE
        //连接url
        this.url = WS_PROTOCOL + '://' + WS_IP + ':' + WS_PORT + '/' + NAMESPACE
        //消息处理器
        this.initHandlerList()
        this.connect(true)
    }

    //开始连接
    connect (isReconncect) {
        //创建websocket
        this.ws = new WebSocket(this.url)
        console.log('current url ' + this.url + ' status ' + this.ws.readyState)
        this.ws.binaryType = this.binaryType
        var websocketObj = this
        //连接成功回调
        this.ws.onopen = function (event) {
            console.log('ws open')
            clearTimeout(websocketObj.reconnectIntervalId)
            //标记连接成功
            websocketObj.isconnected = true
            //创建心跳定时器
            websocketObj.pingIntervalId = setInterval(() => {
                websocketObj.ping()
            }, websocketObj.heartbeatTimeout)
            //主动关闭标志标记为false
            websocketObj.userDisconnect = false
            //发送connect指令
            websocketObj.sendBindMessage()
        }
        //连接关闭回调
        this.ws.onclose = function (event) {
            console.log('ws onclose')
            websocketObj.isconnected = false
            clearInterval(websocketObj.pingIntervalId)
            websocketObj.ws.close()
            //如果不是主动关闭的，则发起重连
            if (!websocketObj.userDisconnect) {
                console.log('reconnect websocket')
                websocketObj.reconnect(event)
            }
        }
        //连接错误回调
        this.ws.onerror = function (event) {
            console.log('connect error')
        }

        //消息回调
        this.ws.onmessage = function (event) {
            console.log(' onmessage[' + event.data + ']')
            websocketObj.processMessage(event.data)
        }
    }

    //重连
    reconnect (event) {
        var websocketObj = this
        websocketObj.reconnectIntervalId = setTimeout(() => {
            websocketObj.connect(true)
        }, this.reconnectInterval)
    }

    //心跳
    ping () {
        let hearBeat = this.messageGenerate.createHeartBeat()
        //this.send(hearBeat.serializeBinary())
    }

    //添加各项消息处理器
    initHandlerList () {
        let handlerMap = new Map()
        handlerMap.set(BIND_REQ, new BindHandler())
        handlerMap.set(BIND_RESP, new BindHandler())
        handlerMap.set(SINGLE_MSG_REQ, new MsgHandler())
        this.baseMessageHandler = new BaseMessageHandler(handlerMap)
    }

    //处理消息
    processMessage (data) {
        //把消息反序列化成protobuf对象
        let msg = proto.Message.deserializeBinary(data)
        //扔到消息处理器分发处理
        this.baseMessageHandler.read(msg)
    }

    /**
     * 链接建立信息
     */
    sendBindMessage () {
        //这个是从store获取用户信息，你可以换你自己的
        let user = vuexStore.getters.userInfo
        //建立绑定消息
        let bindReq = this.messageGenerate.createBindReq(user.id)
        this.send(bindReq)
    }

    //发送消息
    send (data) {
        return new Promise((resolve, reject) => {
            if (this.isconnected) {
            this.ws.send(data.serializeBinary())
            resolve(data)
        } else {
            let err = new Error('current websocket is close')
            reject(err)
        }
    })

    }
}
