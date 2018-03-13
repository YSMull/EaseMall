package com.ysmull.easeshop.dao.impl;

import com.ysmull.easeshop.dao.PurchaseDao;
import com.ysmull.easeshop.model.entity.GoodsSold;
import com.ysmull.easeshop.model.entity.PurchaseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyusu
 */
@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final BeanPropertyRowMapper<PurchaseRecord> ROW_MAPPER = new BeanPropertyRowMapper<>(PurchaseRecord.class);


    @Override
    public void batchInsert(List<PurchaseRecord> purchaseRecords) {
        String sql = "insert into ease_purchase_record(user_id, goods_id, " +
                "snap_goods_name, snap_price, snap_description, snap_detail, snap_pic_url, amount)" +
                " values(:userId, :goodsId, :snapGoodsName, :snapPrice, :snapDescription, :snapDetail, :snapPicUrl, :amount)";
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(purchaseRecords.toArray()));
    }

    @Override
    public List<PurchaseRecord> getHistory(long userId) {
        String sql = "select * from ease_purchase_record where user_id=:user_id";
        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public PurchaseRecord getPurchaseRecord(long snapId, long userId) {
        String sql = "select * from ease_purchase_record where id=:snapId and user_id=:userId";
        Map<String, Object> params = new HashMap<>(4);
        params.put("snapId", snapId);
        params.put("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PurchaseRecord.class));
    }

    @Override
    public List<GoodsSold> getSoldCount(List<Long> goodsIds) {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return Collections.emptyList();
        } else {
            String sql = "select goods_id, sum(amount) sold from ease_purchase_record where goods_id in (:goodsIds) group by goods_id";
            Map<String, Object> params = new HashMap<>(2);
            params.put("goodsIds", goodsIds);
            return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(GoodsSold.class));
        }
    }

    @Override
    public boolean hasBought(long userId, long goodsId) {
        String sql = "select * from ease_purchase_record where user_id=:userId and goods_id=:goodsId fetch first 1 rows only";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        List<PurchaseRecord> res = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
        return !res.isEmpty();
    }
}
