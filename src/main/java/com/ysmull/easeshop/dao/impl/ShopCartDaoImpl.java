package com.ysmull.easeshop.dao.impl;

import com.ysmull.easeshop.dao.ShopCartDao;
import com.ysmull.easeshop.model.entity.ShopCart;
import com.ysmull.easeshop.model.vo.ShopCartVO;
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
public class ShopCartDaoImpl implements ShopCartDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final BeanPropertyRowMapper<ShopCart> ROW_MAPPER = new BeanPropertyRowMapper<>(ShopCart.class);

    @Override
    public List<ShopCart> getCart(long userId) {
        String sql = "select * from ease_cart where user_id=:userId and status=1 order by create_time desc";
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", userId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    @Override
    public ShopCart getCart(long userId, long goodsId) {
        String sql = "select * from ease_cart where user_id=:userId and goods_id=:goodsId and status=1 for update";
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
        String sql = "update ease_cart set amount=:amount" +
                " where user_id=:userId and goods_id=:goodsId and status=1";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        params.put("amount", amount);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void batchDelete(List<ShopCartVO> shopCartVOs) {
        String sql = "update ease_cart set status=0 " +
                "where user_id=:userId and goods_id=:goodsId and status=1";
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(shopCartVOs.toArray()));
    }

    @Override
    public void delete(long userId, long goodsId) {
        String sql = "update ease_cart set status=0" +
                " where user_id=:userId and goods_id=:goodsId and status=1";
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("goodsId", goodsId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
