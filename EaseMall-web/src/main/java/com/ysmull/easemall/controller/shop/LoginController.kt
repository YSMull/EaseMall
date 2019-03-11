package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.UserService
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.model.vo.UserVO
import com.ysmull.easemall.model.vo.WebResponse
import com.ysmull.easemall.util.UserContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
class LoginController(
        val userService: UserService
) {

    private val log = LoggerFactory.getLogger(LoginController::class.java)

    @PostMapping(value = ["/login"])
    @ResponseBody
    fun login(@RequestParam("username") username: String,
              @RequestParam("password") password: String,
              httpResponse: HttpServletResponse): WebResponse<UserVO> {
        val webResponse = WebResponse<UserVO>()
        val cookieValue: String? = userService.check(username, password)
        if (cookieValue != null) {
            val cookie = Cookie("ES_token", cookieValue)
            cookie.path = "/"
            httpResponse.addCookie(cookie)
            log.debug("add login cookie")
            val userVO = UserVO()
            userVO.userId = java.lang.Long.parseLong(cookieValue.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
            webResponse.data = userVO
        } else {
            webResponse.code = WebResponse.NO_AUTH
            webResponse.msg = "login failed!"
        }
        return webResponse
    }

    @GetMapping(value = ["/valid"])
    @Privilege(login = true)
    @ResponseBody
    fun valid(httpResponse: HttpServletResponse): WebResponse<UserVO> {
        val user = UserContext.currentUser
        val webResponse = WebResponse<UserVO>()
        val userVO = UserVO()
        userVO.userId = user.id
        userVO.username = user.username
        userVO.role = user.role?.ordinal
        webResponse.data = userVO
        return webResponse
    }

    @GetMapping(value = ["/logout"])
    fun logout(httpRequest: HttpServletRequest, httpResponse: HttpServletResponse) {
        val cookies = httpRequest.cookies
        for (c in cookies) {
            if ("ES_token" == c.name) {
                c.value = ""
                c.path = "/"
                c.maxAge = 0
                httpResponse.addCookie(c)
                log.debug("delete login cookie")
            }
        }
    }
}
