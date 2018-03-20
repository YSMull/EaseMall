package com.ysmull.easemall.dao.impl;

import com.ysmull.easemall.dao.PurchaseDao;
import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.GoodsSold;
import com.ysmull.easemall.model.entity.PurchaseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
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
public class PurchaseDaoImpl implements PurchaseDao {

    private static final BeanPropertyRowMapper<PurchaseRecord> ROW_MAPPER = new BeanPropertyRowMapper<>(PurchaseRecord.class);
    private static final String FIELDS = "id,user_id,goods_id,snap_goods_name,snap_price,snap_description,snap_detail,snap_pic_url,amount,purchase_time";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void batchInsert(List<PurchaseRecord> purchaseRecords) {
        String sql = "INSERT INTO ease_purchase_record(user_id, goods_id, " +
                "snap_goods_name, snap_price, snap_description, snap_detail, snap_pic_url, amount)" +
                " VALUES(:userId, :goodsId, :snapGoodsName, :snapPrice, :snapDescription, :snapDetail, :snapPicUrl, :amount)";
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(purchaseRecords.toArray()));
    }

    @Override
    public List<PurchaseRecord> getHistory(long userId) {
        String sql = "SELECT " + FIELDS + " FROM ease_purchase_record WHERE user_id=:user_id";
        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public PurchaseRecord getPurchaseRecord(long snapId, long userId) throws RecordNotFoundException {
        String sql = "SELECT " + FIELDS + " FROM ease_purchase_record WHERE id=:snapId AND user_id=:userId";
        Map<String, Object> params = new HashMap<>(4);
        params.put("snapId", snapId);
        params.put("userId", userId);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(PurchaseRecord.class));
        } catch (Exception e) {
            throw new RecordNotFoundException("no record");
        }
    }

    @Override
    public List<GoodsSold> getSoldCount(List<Long> goodsIds) {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return Collections.emptyList();
        } else {
            String sql = "SELECT goods_id, sum(amount) sold FROM ease_purchase_record WHERE goods_id IN (:goodsIds) GROUP BY goods_id";
            Map<String, Object> params = new HashMap<>(2);
            params.put("goodsIds", goodsIds);
            return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(GoodsSold.class));
        }
    }

    @Override
    public PurchaseRecord hasBought(long userId, long goodsId) {
        String sql = "SELECT " + FIELDS + " FROM ease_purchase_record WHERE user_id=:userId AND goods_id=:goodsId FETCH FIRST 1 ROWS ONLY";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        List<PurchaseRecord> res = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
        if (!res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }
}
