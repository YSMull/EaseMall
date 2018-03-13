package com.ysmull.easeshop.dao.impl;

import com.ysmull.easeshop.dao.PicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.Types;

/**
 * @author maoyusu
 */
@Repository
public class PicDaoImpl implements PicDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public void savePic(String picName, String picId, byte[] picBytes) {
        String sql = "insert into ease_goods_pic(uuid, name, pic) values(:uuid, :name, :pic)";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("uuid", picId);
        paramSource.addValue("name", picName, Types.VARCHAR);
        paramSource.addValue("pic", new SqlLobValue(new ByteArrayInputStream(picBytes), picBytes.length, new DefaultLobHandler()), Types.BLOB);
        namedParameterJdbcTemplate.update(sql, paramSource);
    }
}
