package com.ysmull.easemall.dao.impl

import com.ysmull.easemall.dao.UserDao
import com.ysmull.easemall.model.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

import java.util.HashMap

/**
 * @author maoyusu
 */
@Repository
class UserDaoImpl(
        private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : UserDao {

    @Cacheable(cacheNames = ["userCache"], sync = true)
    override fun getUserById(id: Long): User? {
        val sql = "SELECT id,username,password,role FROM ease_shop_user WHERE id = :id"
        val params = HashMap<String, Long>(2)
        params["id"] = id
        return namedParameterJdbcTemplate.queryForObject(sql, MapSqlParameterSource(params), ROW_MAPPER)
    }

    override fun getUserByUserName(username: String): List<User> {
        val sql = "SELECT id,username,password,role FROM ease_shop_user WHERE username = :username"
        val params = HashMap<String, String>(2)
        params["username"] = username
        return namedParameterJdbcTemplate.query(sql, MapSqlParameterSource(params), ROW_MAPPER)
    }

    companion object {

        private val ROW_MAPPER = BeanPropertyRowMapper(User::class.java)
    }
}
