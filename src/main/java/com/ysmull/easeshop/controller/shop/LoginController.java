package com.ysmull.easeshop.controller.shop;

import com.ysmull.easeshop.annotation.Privilege;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.UserVO;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.UserService;
import com.ysmull.easeshop.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(LoginController.class);


    @PostMapping(value = "/login")
    @ResponseBody
    public WebResponse<UserVO> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     HttpServletResponse httpResponse) {
        WebResponse<UserVO> webResponse = new WebResponse<>();
        String cookieValue;
        if ((cookieValue = userService.check(username, password)) != null) {
            Cookie cookie = new Cookie("ES_token", cookieValue);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            log.debug("add login cookie");
            UserVO userVO = new UserVO();
            userVO.setUserId(Long.parseLong(cookieValue.split(":")[0]));
            webResponse.setData(userVO);
        } else {
            webResponse.setCode(WebResponse.NEED_LOGIN);
            webResponse.setMsg("login failed!");
        }
        return webResponse;
    }

    @GetMapping(value = "/valid")
    @Privilege(login = true)
    @ResponseBody
    public WebResponse<UserVO> valid(HttpServletResponse httpResponse) {
        User user = UserContext.getCurrentUser();
        WebResponse<UserVO> webResponse = new WebResponse<>();
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setRole(user.getRole().ordinal());
        webResponse.setData(userVO);
        return webResponse;
    }

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Cookie[] cookies = httpRequest.getCookies();
        for (Cookie c : cookies) {
            if ("ES_token".equals(c.getName())) {
                c.setValue("");
                c.setPath("/");
                c.setMaxAge(0);
                httpResponse.addCookie(c);
                log.debug("delete login cookie");
            }
        }
    }
}
