package com.ysmull.easemall.biz;

import com.ysmull.easemall.model.entity.User;

import javax.servlet.http.Cookie;

/**
 * @author maoyusu
 */
public interface UserService {

    /**
     * 检查密码是否正确
     *
     * @param username   用户名
     * @param password2V 待检查密码
     * @return 若校验成功，则返回cookie，否则返回null
     */
    String check(String username, String password2V);

    /**
     * 根据cookie尝试获取用户
     *
     * @param cookie 前台传入的cookie
     * @return 如果cookie合法，则返回User对象，否则返回null
     */
    User tryGetAuthenticatedUser(Cookie cookie);
}
