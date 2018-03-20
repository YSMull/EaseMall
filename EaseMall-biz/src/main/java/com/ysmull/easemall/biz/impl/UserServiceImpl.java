package com.ysmull.easemall.biz.impl;

import com.ysmull.easemall.biz.UserService;
import com.ysmull.easemall.dao.UserDao;
import com.ysmull.easemall.model.entity.User;
import com.ysmull.easemall.util.PrivilegeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * @author maoyusu
 */
@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;

    @Override
    public String check(String username, String password2V) {
        List<User> users = userDao.getUserByUserName(username);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        if (users.size() > 1) {
            log.error("more than 1 user found!");
        }
        String password = users.get(0).getPassword().toLowerCase();
        password2V = password2V.toLowerCase();
        return password.equals(password2V) ? PrivilegeUtil.generateCookie(users.get(0)) : null;
    }

    @Override
    public User tryGetAuthenticatedUser(Cookie cookie) {
        if (cookie == null) {
            return null;
        }
        Long t = Long.parseLong(cookie.getValue().split(":")[1]);
        if (t < System.currentTimeMillis()) {
            return null;
        }
        int uid = Integer.parseInt(cookie.getValue().split(":")[0]);

        String md5 = cookie.getValue().split(":")[2];
        User user = userDao.getUserById(uid);
        if (user != null) {
            String m1 = PrivilegeUtil.generateHash(user, t);
            if (m1.equals(md5)) {
                return user;
            } else {
                return null;
            }
        }
        return null;
    }
}
