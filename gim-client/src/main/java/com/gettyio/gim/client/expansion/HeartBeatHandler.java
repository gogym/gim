package com.gettyio.gim.client.expansion;

import com.gettyio.core.util.timer.HashedWheelTimer;
import com.gettyio.core.util.timer.Timeout;
import com.gettyio.core.util.timer.TimerTask;
import com.gettyio.gim.client.core.GimContext;

import java.util.concurrent.TimeUnit;

/*
 * 类名：HeartBeatHandler
 * 版权：Copyright by www.getty.com
 * 描述：
 * 修改人：gogym
 * 时间：2020/3/18
 */
public class HeartBeatHandler implements TimerTask {


    GimContext gimContext;
    // 创建一个定时器
    private final HashedWheelTimer timer;

    public HeartBeatHandler(GimContext gimContext) {
        this.gimContext = gimContext;
        this.timer = new HashedWheelTimer();
    }


    public void start() {
        timer.newTimeout(this, gimContext.gimConfig.getHeartBeatInterval(), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        gimContext.messagEmitter.sendHeartBeat();

        //重复调用，维持心跳
        if (!gimContext.socketChannel.isInvalid()){
            timer.newTimeout(this, gimContext.gimConfig.getHeartBeatInterval(), TimeUnit.MILLISECONDS);
        }
    }
}
