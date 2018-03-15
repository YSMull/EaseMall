package com.ysmull.easeshop.controller.shop;

import com.ysmull.easeshop.annotation.Privilege;
import com.ysmull.easeshop.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author maoyusu
 */
@Controller
@RequestMapping("api")
public class PicController {

    private final Logger log = LoggerFactory.getLogger(PicController.class);

    private static final Long MAXPICSIZE = 3 * 1024 * 1000L;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @GetMapping("/pic/{id}")
    @SuppressWarnings("unchecked")
    void getPic(@PathVariable("id") String uuid, HttpServletResponse res) {
        String sql = "SELECT * FROM ease_goods_pic WHERE uuid=:uuid";
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
            log.warn("pic not found");
        } catch (DataAccessException e) {
            log.error("error during pic fetching...");
        }
    }

    @GetMapping("/pic/proxy")
    @Privilege(role = User.ROLE.SELLER)
    void picProxy(@RequestParam("picUrl") String picUrl, HttpServletResponse res) throws IOException {
        URL pic = new URL(picUrl);
        URLConnection conn = pic.openConnection();
        if (conn.getContentLengthLong() < MAXPICSIZE) {
            // 这里要设置content-length，否则递归调用该接口会无法正常返回
            res.setContentLengthLong(conn.getContentLengthLong());
            FileCopyUtils.copy(conn.getInputStream(), res.getOutputStream());
        }

    }
}
