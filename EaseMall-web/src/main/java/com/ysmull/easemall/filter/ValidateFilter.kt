package com.ysmull.easemall.filter

import com.alibaba.fastjson.JSON
import com.google.common.util.concurrent.RateLimiter
import com.ysmull.easemall.biz.UserService
import com.ysmull.easemall.biz.impl.UserServiceImpl
import com.ysmull.easemall.model.vo.WebResponse
import com.ysmull.easemall.util.UserContext
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import java.io.IOException
import javax.annotation.Resource
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @author maoyusu
 */
@Order(1)
@WebFilter(filterName = "validation", urlPatterns = ["/*"], asyncSupported = true)
class ValidateFilter(
        private val userService: UserService
) : Filter {
    private val logger = LoggerFactory.getLogger(ValidateFilter::class.java)
    private val rateLimiter = RateLimiter.create(800.0)
    override fun init(filterConfig: FilterConfig?) {
        // do nothing
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val httpRequest = servletRequest as HttpServletRequest
        if (rateLimiter.tryAcquire()) {
            val reqUrl = httpRequest.requestURI
            if (!reqUrl.startsWith(WEB_RESOURCES_PATH)) {
                logger.info(httpRequest.requestURI)
            }
            rateLimiter.acquire()
            val token: Cookie? = httpRequest.cookies?.find { it.name == "ES_token" }
            val user = userService.tryGetAuthenticatedUser(token)
            UserContext(user).use { filterChain.doFilter(servletRequest, servletResponse) }
        } else {
            val webResponse = WebResponse<String>()
            webResponse.msg = "limit"
            val res = servletResponse as HttpServletResponse
            res.writer.write(JSON.toJSONString(webResponse))
            res.status = HttpStatus.SERVICE_UNAVAILABLE.value()
        }
    }

    override fun destroy() {
        // do nothing
    }

    companion object {

        private val WEB_RESOURCES_PATH = "/dist/"
    }
}
