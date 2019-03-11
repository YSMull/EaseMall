package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.UserService
import com.ysmull.easemall.dao.UserDao
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.util.PrivilegeUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils

import javax.servlet.http.Cookie

/**
 * @author maoyusu
 */
@Service
class UserServiceImpl(
        internal var userDao: UserDao
) : UserService {


    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun check(username: String, password2V: String): String? {
        val users = userDao.getUserByUserName(username)
        if (CollectionUtils.isEmpty(users)) {
            return null
        }
        if (users.size > 1) {
            log.error("more than 1 user found!")
        }
        val password = users[0] .password?.toLowerCase()
        return if (password == password2V.toLowerCase()) PrivilegeUtil.generateCookie(users[0]) else null
    }

    override fun tryGetAuthenticatedUser(cookie: Cookie?): User? {
        if (cookie == null) return null
        val t = java.lang.Long.parseLong(cookie.value.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        if (t < System.currentTimeMillis()) {
            return null
        }
        val uid = Integer.parseInt(cookie.value.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])

        val md5 = cookie.value.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2]
        val user = userDao.getUserById(uid.toLong())
        if (user != null) {
            val m1 = PrivilegeUtil.generateHash(user, t)
            return if (m1 == md5) {
                user
            } else {
                null
            }
        }
        return null
    }
}
