package com.ysmull.easemall.dao.impl;

import com.ysmull.easemall.dao.CartDao;
import com.ysmull.easemall.model.entity.ShopCart;
import com.ysmull.easemall.model.vo.ShopCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyusu
 */
@Repository
public class CartDaoImpl implements CartDao {

    private static final BeanPropertyRowMapper<ShopCart> ROW_MAPPER = new BeanPropertyRowMapper<>(ShopCart.class);
    private static final String FIELDS = "id,user_id,goods_id,amount,status,create_time,update_time";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ShopCart> getCart(long userId) {
        String sql = "SELECT " + FIELDS + " FROM ease_cart WHERE user_id=:userId AND status=1 ORDER BY create_time DESC";
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", userId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public ShopCart getCart(long userId, long goodsId) {
        String sql = "SELECT " + FIELDS + " FROM ease_cart WHERE user_id=:userId AND goods_id=:goodsId AND status=1 FOR UPDATE";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        List<ShopCart> shopCarts = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
        if (CollectionUtils.isEmpty(shopCarts)) {
            return null;
        } else {
            return shopCarts.get(0);
        }
    }

    @Override
    public void addCart(long userId, long goodsId, long amount) {
        String sql = "INSERT INTO ease_cart(user_id, goods_id, amount) " +
                "VALUES(:userId,:goodsId,:amount)";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        params.put("amount", amount);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void changeCartAmount(long userId, long goodsId, long amount) {
        String sql = "UPDATE ease_cart SET amount=:amount" +
                " WHERE user_id=:userId AND goods_id=:goodsId AND status=1";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        params.put("amount", amount);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void batchDelete(List<ShopCartVO> shopCartVOs) {
        String sql = "UPDATE ease_cart SET status=0 " +
                "WHERE user_id=:userId AND goods_id=:goodsId AND status=1";
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(shopCartVOs.toArray()));
    }

    @Override
    public void delete(long userId, long goodsId) {
        String sql = "UPDATE ease_cart SET status=0" +
                " WHERE user_id=:userId AND goods_id=:goodsId AND status=1";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
