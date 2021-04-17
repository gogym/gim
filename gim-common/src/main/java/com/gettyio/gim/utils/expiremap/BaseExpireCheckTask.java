package com.gettyio.gim.utils.expiremap;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName BaseExpireCheckTask.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 15:18:00
 */
public abstract class BaseExpireCheckTask<K, V> extends Thread {

    ConcurrentHashMap<K, Long> delayMap = null;
    ConcurrentHashMap<K, V> expireMap = null;

    public BaseExpireCheckTask(ConcurrentHashMap<K, V> expireMap, ConcurrentHashMap<K, Long> delayMap) {
        this.delayMap = delayMap;
        this.expireMap = expireMap;
    }

    protected abstract void expireEvent(K key, V val);

    @Override
    public void run() {
        Iterator<K> it = null;
        K key = null;
        while (true) {
            if (delayMap != null && !delayMap.isEmpty()) {
                it = delayMap.keySet().iterator();
                while (it.hasNext()) {
                    key = it.next();
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
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }

}
