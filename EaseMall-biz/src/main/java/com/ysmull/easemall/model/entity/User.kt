package com.ysmull.easemall.model.entity

import com.ysmull.easemall.annotation.NoArg

/**
 * @author maoyusu
 */

@NoArg
data class User(
        var password: String,
        var id: Long,
        var username: String,
        var role: ROLE) {

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\''.toString() +
                ", password='" + password + '\''.toString() +
                ", role=" + role +
                '}'.toString()
    }

    enum class ROLE {
        /**
         * 买家
         */
        BUYER,
        /**
         * 卖家
         */
        SELLER

    }
}
