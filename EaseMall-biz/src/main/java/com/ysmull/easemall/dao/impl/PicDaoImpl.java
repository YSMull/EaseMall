package com.ysmull.easemall.dao.impl;

import com.ysmull.easemall.dao.PicDao;
import com.ysmull.easemall.exception.PicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO ease_goods_pic(uuid, name, pic) VALUES(:uuid, :name, :pic)";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("uuid", picId);
        paramSource.addValue("name", picName, Types.VARCHAR);
        paramSource.addValue("pic", new SqlLobValue(new ByteArrayInputStream(picBytes), picBytes.length, new DefaultLobHandler()), Types.BLOB);
        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getPic(String uuid, HttpServletResponse res) throws PicNotFoundException {
        String sql = "SELECT id, uuid, name, pic FROM ease_goods_pic WHERE uuid=:uuid";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("uuid", uuid);
        try {
            namedParameterJdbcTemplate.query(sql, parameterSource, new AbstractLobStreamingResultSetExtractor() {
                @Override
                public void streamData(ResultSet rs) throws SQLException, IOException {
                    FileCopyUtils.copy(new DefaultLobHandler().getBlobAsBinaryStream(rs, 4), res.getOutputStream());
                }
            });
        } catch (EmptyResultDataAccessException e) {
            throw new PicNotFoundException("pic not found");
        } catch (DataAccessException e) {
            throw new PicNotFoundException("error during pic fetching...");
        }
    }
}
