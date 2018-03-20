package com.ysmull.easemall.dao.impl;

import com.ysmull.easemall.dao.GoodsDao;
import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyusu
 */
@Repository
public class GoodsDaoImpl implements GoodsDao {

    private static final BeanPropertyRowMapper<Goods> ROW_MAPPER = new BeanPropertyRowMapper<>(Goods.class);
    private static final String FIELDS = "id,name,price,description,detail,pic_url,publisher,status";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Goods> getAllGoods() {
        String sql = "SELECT " + FIELDS + " FROM ease_goods_info WHERE status=1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public List<Goods> getPublishedGoods(long publisher) {
        String sql = "SELECT " + FIELDS + " FROM ease_goods_info WHERE publisher=:publisher AND status=1";
        Map<String, Object> params = new HashMap<>(2);
        params.put("publisher", publisher);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public List<Goods> getGoodsByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        } else {
            String sql = "SELECT " + FIELDS + " FROM ease_goods_info WHERE id IN (:ids)";
            Map<String, Object> params = new HashMap<>(2);
            params.put("ids", ids);
            return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
        }
    }

    @Override
    public Goods get(long goodsId) throws RecordNotFoundException {
        String sql = "SELECT " + FIELDS + " FROM ease_goods_info WHERE id =:goodsId AND status = 1";
        Map<String, Object> params = new HashMap<>(2);
        params.put("goodsId", goodsId);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
        } catch (Exception e) {
            throw new RecordNotFoundException("no record");
        }
    }

    @Override
    public long insert(Goods goods) {
        String sql = "INSERT INTO ease_goods_info(name, price, description, pic_url, detail, publisher) " +
                "VALUES(:name, :price, :description, :picUrl, :detail, :publisher)";
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", goods.getName());
        params.addValue("price", goods.getPrice());
        params.addValue("description", goods.getDescription());
        params.addValue("picUrl", goods.getPicUrl());
        params.addValue("detail", goods.getDetail());
        params.addValue("publisher", goods.getPublisher());
        namedParameterJdbcTemplate.update(sql, params, holder);
        return holder.getKey().longValue();
    }

    @Override
    public void update(Goods goods) {
        String sql = "UPDATE ease_goods_info SET name=:name, price=:price, description=:description, pic_url=:picUrl, detail=:detail, publisher=:publisher" +
                " WHERE id=:id";
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", goods.getId());
        params.addValue("name", goods.getName());
        params.addValue("price", goods.getPrice());
        params.addValue("description", goods.getDescription());
        params.addValue("picUrl", goods.getPicUrl());
        params.addValue("detail", goods.getDetail());
        params.addValue("publisher", goods.getPublisher());
        namedParameterJdbcTemplate.update(sql, params, holder);

    }

    @Override
    public void delete(long goodsId) {
        String sql = "UPDATE ease_goods_info SET status=0 WHERE id=:goodsId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("goodsId", goodsId);
        namedParameterJdbcTemplate.update(sql, params);
    }


}
