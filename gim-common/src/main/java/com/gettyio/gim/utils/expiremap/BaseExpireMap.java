package com.gettyio.gim.utils.expiremap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName BaseExpireMap.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 15:15:00
 */
public abstract class BaseExpireMap<K, V> {
    private final long expTime;
    private final TimeUnit unit;
    /**
     * 线程安全的map容器
     */
    ConcurrentHashMap<K, V> expireMap;
    /**
     * 控制过期时间的map
     */
    ConcurrentHashMap<K, Long> delayMap;

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();


    /**
     * 将map提供给外部程序操作
     *
     * @return
     * @Title: getDataMap
     * @Description: TODO
     * @return: ConcurrentHashMap<K, V>
     */
    public ConcurrentHashMap<K, V> getDataMap() {
        return this.expireMap;
    }

    public BaseExpireMap(long expTime, TimeUnit unit) {
        expireMap = new ConcurrentHashMap<K, V>();
        delayMap = new ConcurrentHashMap<K, Long>();
        this.expTime = expTime;
        this.unit = unit;
        // 启动监听线程
        BaseExpireCheckTask<K, V> task = new BaseExpireCheckTask<K, V>(expireMap, delayMap,lock,cond) {
            @Override
            protected void expireEvent(K key, V val) {
                baseExpireEvent(key, val);
            }
        };
        task.setDaemon(false);
        task.start();
    }

    /**
     * 过期事件 子类实现
     *
     * @param key
     * @Title: baseExpireEvent
     * @Description:
     * @return: void
     */
    protected abstract void baseExpireEvent(K key, V val);

    public V put(K key, V val) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            delayMap.put(key, getExpireTime());
            V putVal = expireMap.put(key, val);
            cond.signal();
            return putVal;
        } finally {
            lock.unlock();
        }
    }

    public V remove(K key) {
        return expireMap.remove(key);
    }

    public V get(K key) {
        return expireMap.get(key);
    }

    private Long getExpireTime() {
        return unit.toMillis(expTime) + System.currentTimeMillis();
    }

}
