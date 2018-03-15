package com.ysmull.easeshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void insert() {
        String insertSql = "INSERT INTO ease_test(name) VALUES(:threadName)";
        MapSqlParameterSource insertParams = new MapSqlParameterSource();
        insertParams.addValue("threadName", Thread.currentThread().toString());
        namedParameterJdbcTemplate.update(insertSql, insertParams);
    }

    public Long selectId() {
        String sql = "select max(id) from ease_test";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public String selectName(Long id) {
        String sql = "select name from ease_test where id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }
}
