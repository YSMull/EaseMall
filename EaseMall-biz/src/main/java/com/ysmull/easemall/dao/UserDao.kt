package com.ysmull.easemall.dao

import com.ysmull.easemall.model.entity.User


/**
 * @author maoyusu
 */
interface UserDao {

    /**
     * 根据用户id查找用户
     *
     * @param id 用户id
     * @return User
     */
    fun getUserById(id: Long): User?

    /**
     * 根据用户账户名查找账户
     *
     * @param username 账户名
     * @return 账户列表
     */
    fun getUserByUserName(username: String): List<User>

}
