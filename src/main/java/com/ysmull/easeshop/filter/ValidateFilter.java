package com.ysmull.easeshop.filter;

import com.alibaba.fastjson.JSON;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.UserService;
import com.ysmull.easeshop.util.PrivilegeUtil;
import com.ysmull.easeshop.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author maoyusu
 */
@Order(1)
@WebFilter(filterName = "validation", urlPatterns = "/*", asyncSupported = true)
public class ValidateFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(ValidateFilter.class);

    @Resource
    private UserService userService;

    private static final List<String> NEED_LOGIN_API = new ArrayList<>();

    static {
        NEED_LOGIN_API.add("/api/getcart");
        NEED_LOGIN_API.add("/api/addcart");
        NEED_LOGIN_API.add("/api/changecart");
        NEED_LOGIN_API.add("/api/deletecart");
        NEED_LOGIN_API.add("/api/history");
        NEED_LOGIN_API.add("/api/buy");
        NEED_LOGIN_API.add("/api/hasbought");
        NEED_LOGIN_API.add("/api/history");
        NEED_LOGIN_API.add("/api/snapshot");
    }


    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        logger.info(httpRequest.getRequestURI());

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

        String requestURI = httpRequest.getRequestURI();
        if (!"/api/login".equals(requestURI)) {
            User user = userService.tryGetAuthenticatedUser(token);
            try (UserContext context = new UserContext(user)) {
                if (!"/api/valid".equals(requestURI) && user == null && PrivilegeUtil.needLogin(NEED_LOGIN_API,requestURI)) {
                    logger.info("no auth!");
                    WebResponse<String> webResponse = new WebResponse<>();
                    webResponse.setCode(WebResponse.NO_AUTH);
                    webResponse.setMsg("no auth!");

                    servletResponse.getWriter().write(JSON.toJSONString(webResponse));
                    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                } else {
                    //doFilter必须放在try语句中，否则try语句块提前结束UserContext会被销毁，不要在此处catch后续异常
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
