package com.ysmull.easemall.dao;

import com.ysmull.easemall.model.entity.User;

import java.util.List;


/**
 * @author maoyusu
 */
public interface UserDao {

    /**
     * 根据用户id查找用户
     *
     * @param id 用户id
     * @return User
     */
    User getUserById(long id);

    /**
     * 根据用户账户名查找账户
     *
     * @param username 账户名
     * @return 账户列表
     */
    List<User> getUserByUserName(String username);

}
