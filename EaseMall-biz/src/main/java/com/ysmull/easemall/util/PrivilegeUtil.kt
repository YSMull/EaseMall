package com.ysmull.easemall.util

import com.alibaba.fastjson.JSON
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.model.vo.WebResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.xml.bind.DatatypeConverter

/**
 * @author maoyusu
 */
class PrivilegeUtil private constructor() {

    init {
        throw IllegalStateException("Utility class")
    }

    companion object {

        private val log = LoggerFactory.getLogger(PrivilegeUtil::class.java)

        /**
         * 五分钟登陆过期
         */
        private const val EXTERIOR = (5 * 60 * 1000).toLong()
        private const val SECRET = "MYS&^e%!"

        fun generateCookie(user: User): String {
            val uid = user.id
            val password = user.password
            val t = System.currentTimeMillis() + EXTERIOR

            val joinedStr = arrayOf(uid!!.toString(), password, t.toString(), SECRET).joinToString(":")
            log.debug(joinedStr)

            var md5 = ""
            try {
                md5 = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5")
                        .digest(joinedStr.toByteArray(charset("UTF-8"))))
            } catch (e: NoSuchAlgorithmException) {
                log.error("md5 generate failed")
                log.error(e.toString())
            } catch (e: UnsupportedEncodingException) {
                log.error("md5 generate failed")
                log.error(e.toString())
            }

            val md5Cookie = arrayOf(uid.toString(), t.toString(), md5).joinToString(":")
            log.debug("md5Cookie:{}", md5Cookie)
            return md5Cookie
        }

        fun generateHash(user: User, expiredTime: Long): String {
            val uid = user.id
            val password = user.password

            val joinedStr = arrayOf(uid!!.toString(), password, expiredTime.toString(), SECRET).joinToString(":")
            var md5 = ""
            try {
                md5 = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5")
                        .digest(joinedStr.toByteArray(charset("UTF-8"))))
            } catch (e: NoSuchAlgorithmException) {
                log.error("md5 generate failed")
                log.error(e.toString())
            } catch (e: UnsupportedEncodingException) {
                log.error("md5 generate failed")
                log.error(e.toString())
            }

            return md5
        }

        @Throws(IOException::class)
        fun noAuth(roles: Array<User.ROLE>): Any? {
            val requestAttributes = RequestContextHolder.getRequestAttributes()
            val httpResponse = (requestAttributes as ServletRequestAttributes).response
            val webResponse = WebResponse<String>()
            webResponse.code = WebResponse.NO_AUTH

            if (roles.size == 0) {
                webResponse.msg = "need login"
            } else {
                val msg = roles.map { it.toString() }.joinToString(",")
                webResponse.msg = String.format("need %s", msg)
            }
            httpResponse!!.writer.write(JSON.toJSONString(webResponse))
            httpResponse.status = HttpStatus.UNAUTHORIZED.value()
            return null
        }
    }

}
