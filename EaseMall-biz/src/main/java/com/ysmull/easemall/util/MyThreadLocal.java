package com.ysmull.easemall.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maoyusu
 */
public class MyThreadLocal<T> {

    // 如果使用Thread作为key，当线程非常多多时候，map大小会非常大
    // 而JDK自带的ThreadLocal使用threadLocal本身作为key，ThreadLocal的数量为map的大小

    private static Map<Thread, Object> threadLocalMap = new ConcurrentHashMap<>();

    public void set(T value) {
        threadLocalMap.put(Thread.currentThread(), value);
    }

    public T get() {
        @SuppressWarnings("unchecked")
        T result = (T) threadLocalMap.get(Thread.currentThread());
        return result;
    }

    public void remove() {
        threadLocalMap.remove(Thread.currentThread());
    }
}

