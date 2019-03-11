package com.ysmull.easemall.dao.impl

import com.ysmull.easemall.dao.CartDao
import com.ysmull.easemall.model.entity.ShopCart
import com.ysmull.easemall.model.vo.ShopCartVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
import org.springframework.stereotype.Repository
import org.springframework.util.CollectionUtils

import java.util.HashMap

/**
 * @author maoyusu
 */
@Repository
class CartDaoImpl(
        private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : CartDao {
    
    override fun getCart(userId: Long): List<ShopCart> {
        val sql = "SELECT $FIELDS FROM ease_cart WHERE user_id=:userId AND status=1 ORDER BY create_time DESC"
        val params = HashMap<String, Any>(2)
        params["userId"] = userId
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
    }

    override fun getCart(userId: Long, goodsId: Long): ShopCart? {
        val sql = "SELECT $FIELDS FROM ease_cart WHERE user_id=:userId AND goods_id=:goodsId AND status=1 FOR UPDATE"
        val params = HashMap<String, Any>(4)
        params["userId"] = userId
        params["goodsId"] = goodsId
        val shopCarts = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
        return if (CollectionUtils.isEmpty(shopCarts)) {
            null
        } else {
            shopCarts[0]
        }
    }

    override fun addCart(userId: Long, goodsId: Long, amount: Long) {
        val sql = "INSERT INTO ease_cart(user_id, goods_id, amount) " + "VALUES(:userId,:goodsId,:amount)"
        val params = HashMap<String, Any>(4)
        params["userId"] = userId
        params["goodsId"] = goodsId
        params["amount"] = amount
        namedParameterJdbcTemplate.update(sql, params)
    }

    override fun changeCartAmount(userId: Long, goodsId: Long, amount: Long) {
        val sql = "UPDATE ease_cart SET amount=:amount" + " WHERE user_id=:userId AND goods_id=:goodsId AND status=1"
        val params = HashMap<String, Any>(4)
        params["userId"] = userId
        params["goodsId"] = goodsId
        params["amount"] = amount
        namedParameterJdbcTemplate.update(sql, params)
    }

    override fun batchDelete(shopCartVOs: List<ShopCartVO>) {
        val sql = "UPDATE ease_cart SET status=0 " + "WHERE user_id=:userId AND goods_id=:goodsId AND status=1"
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(*shopCartVOs.toTypedArray()))
    }

    override fun delete(userId: Long, goodsId: Long) {
        val sql = "UPDATE ease_cart SET status=0" + " WHERE user_id=:userId AND goods_id=:goodsId AND status=1"
        val params = HashMap<String, Any>(4)
        params["userId"] = userId
        params["goodsId"] = goodsId
        namedParameterJdbcTemplate.update(sql, params)
    }

    companion object {

        private val ROW_MAPPER = BeanPropertyRowMapper(ShopCart::class.java)
        private val FIELDS = "id,user_id,goods_id,amount,status,create_time,update_time"
    }
}
