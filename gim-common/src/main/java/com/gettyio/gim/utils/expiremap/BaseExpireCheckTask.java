package com.gettyio.gim.utils.expiremap;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName BaseExpireCheckTask.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 15:18:00
 */
abstract class BaseExpireCheckTask<K, V> extends Thread {

    ConcurrentHashMap<K, Long> delayMap;
    ConcurrentHashMap<K, V> expireMap;

    private final ReentrantLock lock;
    private final Condition cond;

    public BaseExpireCheckTask(ConcurrentHashMap<K, V> expireMap, ConcurrentHashMap<K, Long> delayMap, ReentrantLock lock, Condition cond) {
        this.delayMap = delayMap;
        this.expireMap = expireMap;
        this.lock = lock;
        this.cond = cond;
    }

    protected abstract void expireEvent(K key, V val);

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (delayMap == null || delayMap.isEmpty()) {
                    try {
                        cond.await();
                    } catch (InterruptedException e) {
                        cond.signal();
                        continue;
                    }
                }
                Iterator<K> it = delayMap.keySet().iterator();
                while (it.hasNext()) {
                    K key = it.next();
                    // 元素超时
                    if (delayMap.get(key) <= System.currentTimeMillis()) {
                        // 触发回调
                        expireEvent(key, expireMap.get(key));
                        // 移除
                        it.remove();
                        expireMap.remove(key);
                        delayMap.remove(key);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
