package com.ysmull.easeshop.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.UserService;
import com.ysmull.easeshop.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author maoyusu
 */
@Order(1)
@WebFilter(filterName = "validation", urlPatterns = "/*", asyncSupported = true)
public class ValidateFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(ValidateFilter.class);

    private final RateLimiter rateLimiter = RateLimiter.create(500.0);

    @Resource
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (rateLimiter.tryAcquire()) {
            logger.info(httpRequest.getRequestURI());
            rateLimiter.acquire();
            Cookie[] cookies = httpRequest.getCookies();
            Cookie token = null;
            if (cookies != null && cookies.length > 0) {
                for (Cookie c : cookies) {
                    if ("ES_token".equals(c.getName())) {
                        token = c;
                        break;
                    }
                }
            }
            User user = userService.tryGetAuthenticatedUser(token);
            try (UserContext context = new UserContext(user)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            WebResponse<String> webResponse = new WebResponse<>();
            webResponse.setMsg("limit");
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.getWriter().write(JSON.toJSONString(webResponse));
            res.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
