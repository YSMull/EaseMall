package com.ysmull.easemall.util;

import com.ysmull.easemall.model.entity.User;

/**
 * @author maoyusu
 */
public class UserContext implements AutoCloseable {

    /**
     * 这里故意替换了ThreadLocal，做了个实验
     */
    private static final MyThreadLocal<User> CURRENT = new MyThreadLocal<>();

    public UserContext(User user) {
        if (user != null) {
            CURRENT.set(user);
        }
    }

    public static User getCurrentUser() {
        return CURRENT.get();
    }

    @Override
    public void close() {
        CURRENT.remove();
    }
}
