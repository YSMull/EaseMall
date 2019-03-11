package com.ysmull.easemall.dao.impl

import com.ysmull.easemall.dao.PicDao
import com.ysmull.easemall.exception.PicNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor
import org.springframework.jdbc.core.support.SqlLobValue
import org.springframework.jdbc.support.lob.DefaultLobHandler
import org.springframework.stereotype.Repository
import org.springframework.util.FileCopyUtils

import javax.servlet.http.HttpServletResponse
import java.io.ByteArrayInputStream
import java.io.IOException
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

/**
 * @author maoyusu
 */
@Repository
class PicDaoImpl(
        private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : PicDao {
    

    override fun savePic(picName: String, picId: String, picBytes: ByteArray) {
        val sql = "INSERT INTO ease_goods_pic(uuid, name, pic) VALUES(:uuid, :name, :pic)"
        val paramSource = MapSqlParameterSource()
        paramSource.addValue("uuid", picId)
        paramSource.addValue("name", picName, Types.VARCHAR)
        paramSource.addValue("pic", SqlLobValue(ByteArrayInputStream(picBytes), picBytes.size, DefaultLobHandler()), Types.BLOB)
        namedParameterJdbcTemplate.update(sql, paramSource)
    }

    @Throws(PicNotFoundException::class)
    override fun getPic(uuid: String, res: HttpServletResponse) {
        val sql = "SELECT id, uuid, name, pic FROM ease_goods_pic WHERE uuid=:uuid"
        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("uuid", uuid)
        try {
            namedParameterJdbcTemplate.query(sql, parameterSource, object : AbstractLobStreamingResultSetExtractor<Any>() {
                @Throws(SQLException::class, IOException::class)
                override fun streamData(rs: ResultSet) {
                    FileCopyUtils.copy(DefaultLobHandler().getBlobAsBinaryStream(rs, 4)!!, res.outputStream)
                }
            })
        } catch (e: EmptyResultDataAccessException) {
            throw PicNotFoundException("pic not found")
        } catch (e: DataAccessException) {
            throw PicNotFoundException("error during pic fetching...")
        }

    }
}
