package com.ysmull.easeshop.dao.impl;

import com.ysmull.easeshop.dao.UserDao;
import com.ysmull.easeshop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyusu
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    @Override
    @Cacheable(cacheNames = "userCache", sync = true)
    public User getUserById(long id) {
        String sql = "SELECT id,username,password,role FROM ease_shop_user WHERE id = :id";
        Map<String, Long> params = new HashMap<>(2);
        params.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params), ROW_MAPPER);
    }

    @Override
    public List<User> getUserByUserName(String username) {
        String sql = "SELECT id,username,password,role FROM ease_shop_user WHERE username = :username";
        Map<String, String> params = new HashMap<>(2);
        params.put("username", username);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params), ROW_MAPPER);
    }
}
