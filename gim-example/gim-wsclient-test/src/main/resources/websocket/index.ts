import {
    WS_PROTOCOL,
    WS_IP,
    WS_PORT,
    HEART_BEAT_INTERVAL,
    RECONNECT_INTERVAL,
    BINTRAY_TYPE,
    NAMESPACE
} from './config'
import vuexStore from '../store'

import {BIND_REQ, BIND_RESP, SINGLE_MSG_REQ} from './reqType'
import BindHandler from './handler/bs/BindHandler'
import BaseMessageHandler from './handler/BaseMessageHandler'
import MsgHandler from './handler/bs/MsgHandler'
import MessageGenerate from "./pb/MessageGenerate";
import Timeout = NodeJS.Timeout;
import {Message} from "@/websocket/pb/Message_pb";
import {ErrorInfo} from "ts-loader/dist/interfaces";

export default class VueWebSocket {
    static instance: VueWebSocket
    //主动关闭标志，如果是主动关闭连接，则设置为true。
    userDisconnect = false
    isconnected = false
    messageGenerate: MessageGenerate
    baseMessageHandler: BaseMessageHandler

    ws_protocol: string
    ip: string
    port: number
    heartbeatTimeout: number
    reconnectInterval: number
    binaryType: BinaryType
    url: string

    ws: WebSocket
    reconnectIntervalId: Timeout
    pingIntervalId: Timeout


    // 单例
    static getInstance() {
        this.instance = this.instance ? this.instance : new VueWebSocket()
        return this.instance
    }

    //构造方法，实例化后会立即执行该方法
    constructor() {
        this.messageGenerate = MessageGenerate.getInstance()
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
        this.connect(false)
    }

    //开始连接
    connect(isReconncect: boolean) {
        //创建websocket
        this.ws = new WebSocket(this.url)
        this.ws.binaryType = this.binaryType
        const websocketObj = this;
        //连接成功回调
        this.ws.onopen = function (event) {
            console.log('ws open')
            clearTimeout(websocketObj.reconnectIntervalId)
            //标记连接成功
            websocketObj.isconnected = true
            //创建心跳定时器
            // websocketObj.pingIntervalId = setInterval(() => {
            //     websocketObj.ping()
            // }, websocketObj.heartbeatTimeout)
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
            websocketObj.processMessage(event.data)
        }
    }

    //重连
    reconnect(event: Event) {
        let websocketObj = this
        websocketObj.reconnectIntervalId = setTimeout(() => {
            websocketObj.connect(true)
        }, this.reconnectInterval)
    }

    //心跳
    ping() {
        let hearBeat = this.messageGenerate.createHeartBeat()
        //this.send(hearBeat.serializeBinary())
    }

    initHandlerList() {
        let handlerMap = new Map()
        handlerMap.set(BIND_REQ, new BindHandler())
        handlerMap.set(BIND_RESP, new BindHandler())
        handlerMap.set(SINGLE_MSG_REQ, new MsgHandler())
        this.baseMessageHandler = new BaseMessageHandler(handlerMap)
    }

    processMessage(data: any) {
        try {
            //解码前需要转换
            let dataBuff = new Uint8Array(data)
            let msg = Message.decode(dataBuff)
            this.baseMessageHandler.read(msg)
        } catch (e) {
            console.log(e)
        }
    }

    /**
     * 链接建立信息
     */
    sendBindMessage() {
        let user = vuexStore.state.user.info
        let bindReq = this.messageGenerate.createBindReq(user.id)
        return this.send(bindReq)
    }

    //发送消息
    send(data: Message) {
        return new Promise((resolve, reject) => {
            if (this.isconnected) {
                let msg: Uint8Array = Message.encode(data).finish()
                this.ws.send(msg)
                resolve(data)
            } else {
                let err = new Error('current websocket is close')
                reject(err)
            }
        })
    }


}
