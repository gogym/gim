package com.gettyio.gim.utils.expiremap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName BaseExpireMap.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 15:15:00
 */
public abstract class BaseExpireMap<K, V> {
    private long expTime = 0L;
    private TimeUnit unit = null;
    /**
     * 线程安全的map容器
     */
    ConcurrentHashMap<K, V> expireMap = null;
    /**
     * 控制过期时间
     */
    ConcurrentHashMap<K, Long> delayMap = null;

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
        BaseExpireCheckTask task = new BaseExpireCheckTask<K, V>(expireMap, delayMap) {

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

    public V put(K key, V val) {
        delayMap.put(key, getExpireTime());
        return expireMap.put(key, val);
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
