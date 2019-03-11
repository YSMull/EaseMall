package com.ysmull.easemall

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TestDao(
        internal var namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {
    fun insert() {
        val insertSql = "INSERT INTO ease_test(name) VALUES(:threadName)"
        val insertParams = MapSqlParameterSource()
        insertParams.addValue("threadName", Thread.currentThread().toString())
        namedParameterJdbcTemplate.update(insertSql, insertParams)
    }

    fun selectId(): Long? {
        val sql = "select max(id) from ease_test"
        val params = MapSqlParameterSource()
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long::class.java)
    }

    fun selectName(id: Long?): String? {
        val sql = "select name from ease_test where id=:id"
        val params = MapSqlParameterSource()
        params.addValue("id", id)
        return namedParameterJdbcTemplate.queryForObject(sql, params, String::class.java)
    }
}
