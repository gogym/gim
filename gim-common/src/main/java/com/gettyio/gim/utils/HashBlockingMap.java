package com.gettyio.gim.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class HashBlockingMap<V> {

    /**
     * map结构
     */
    private final ConcurrentMap<Object, Item<V>> map;

    /**
     * 可重入锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    public HashBlockingMap() {
        map = new ConcurrentHashMap<Object, Item<V>>();
    }

    /**
     * 添加一个元素
     *
     * @param key
     * @param o
     * @throws InterruptedException
     */
    public void put(Object key, V o) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            Item<V> item;
            if (map.containsKey(key)) {
                item = map.get(key);
            } else {
                item = new Item<V>();
                map.put(key, item);
            }
            item.put(o);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取一个元素，没有则阻塞
     *
     * @param key
     * @return
     * @throws InterruptedException
     */
    public V take(Object key) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            if (!map.containsKey(key)) {
                map.put(key, new Item<V>());
            }
        } finally {
            lock.unlock();
        }

        Item<V> item = map.get(key);
        V x = item.take();
        map.remove(key);

        return x;
    }

    /**
     * 获取一个元素，没有则阻塞指定时间
     *
     * @param key
     * @param timeout 0 表示不阻塞
     * @return
     * @throws InterruptedException
     */
    public V poll(Object key, long timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            if (!map.containsKey(key)) {
                map.put(key, new Item<V>());
            }
        } finally {
            lock.unlock();
        }

        Item<V> item = map.get(key);
        V x = item.poll(timeout);
        map.remove(key);

        return x;
    }

    /**
     * 内部类
     *
     * @param <E>
     */
    private static class Item<E> {

        private final ReentrantLock lock = new ReentrantLock();

        private final Condition cond = lock.newCondition();

        private E obj = null;

        private void put(E o) throws InterruptedException {
            if (o == null) {
                throw new NullPointerException();
            }
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                obj = o;
                cond.signal();
            } finally {
                lock.unlock();
            }
        }

        E take() throws InterruptedException {
            E x;
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                try {
                    while (obj == null) {
                        cond.await();
                    }
                } catch (InterruptedException ie) {
                    cond.signal();
                    throw ie;
                }
                x = obj;
            } finally {
                lock.unlock();
            }
            return x;
        }

        private E poll(long timeout) throws InterruptedException {
            timeout = TimeUnit.MILLISECONDS.toNanos(timeout);
            E x;
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                for (; ; ) {
                    if (obj != null) {
                        x = obj;
                        break;
                    }
                    if (timeout <= 0) {
                        return null;
                    }
                    try {
                        timeout = cond.awaitNanos(timeout);
                    } catch (InterruptedException ie) {
                        cond.signal();
                        throw ie;
                    }
                }
            } finally {
                lock.unlock();
            }
            return x;
        }
    }

}
