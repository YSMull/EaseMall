package com.ysmull.easemall.util

import com.ysmull.easemall.model.entity.User

/**
 * @author maoyusu
 */
class UserContext(user: User?) : AutoCloseable {

    init {
        if (user != null) {
            CURRENT.set(user)
        }
    }

    override fun close() {
        CURRENT.remove()
    }

    companion object {

        /**
         * 这里故意替换了ThreadLocal，做了个实验
         */
        private val CURRENT = ThreadLocal<User>()

        val currentUser: User
            get() = CURRENT.get()
    }
}
