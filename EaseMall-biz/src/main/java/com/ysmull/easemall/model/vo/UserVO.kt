package com.ysmull.easemall.model.vo

/**
 * @author maoyusu
 */
class UserVO {

    var userId: Long? = null
    var username: String? = null
    var role: Int? = null

    override fun toString(): String {
        return "UserVO{" +
                "userId=" + userId +
                ", username='" + username + '\''.toString() +
                ", role=" + role +
                '}'.toString()
    }
}
