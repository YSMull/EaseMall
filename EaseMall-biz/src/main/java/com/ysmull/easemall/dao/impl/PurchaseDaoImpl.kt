package com.ysmull.easemall.dao.impl

import com.ysmull.easemall.dao.PurchaseDao
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.GoodsSold
import com.ysmull.easemall.model.entity.PurchaseRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
import org.springframework.stereotype.Repository
import org.springframework.util.CollectionUtils

import java.util.Collections
import java.util.HashMap

/**
 * @author maoyusu
 */
@Repository
class PurchaseDaoImpl(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : PurchaseDao {
    override fun batchInsert(purchaseRecords: List<PurchaseRecord>) {
        val sql = "INSERT INTO ease_purchase_record(user_id, goods_id, " +
                "snap_goods_name, snap_price, snap_description, snap_detail, snap_pic_url, amount)" +
                " VALUES(:userId, :goodsId, :snapGoodsName, :snapPrice, :snapDescription, :snapDetail, :snapPicUrl, :amount)"
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(*purchaseRecords.toTypedArray()))
    }

    override fun getHistory(userId: Long): List<PurchaseRecord> {
        val sql = "SELECT $FIELDS FROM ease_purchase_record WHERE user_id=:user_id"
        val params = HashMap<String, Any>(2)
        params["user_id"] = userId
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
    }

    @Throws(RecordNotFoundException::class)
    override fun getPurchaseRecord(snapId: Long, userId: Long): PurchaseRecord {
        val sql = "SELECT $FIELDS FROM ease_purchase_record WHERE id=:snapId AND user_id=:userId"
        val params = HashMap<String, Any>(4)
        params["snapId"] = snapId
        params["userId"] = userId
        val res = namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper(PurchaseRecord::class.java))
        if (res != null) return res;
        else throw RecordNotFoundException("no record")
    }

    override fun getSoldCount(goodsIds: List<Long>): List<GoodsSold> {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return emptyList()
        } else {
            val sql = "SELECT goods_id, sum(amount) sold FROM ease_purchase_record WHERE goods_id IN (:goodsIds) GROUP BY goods_id"
            val params = HashMap<String, Any>(2)
            params["goodsIds"] = goodsIds
            return namedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper(GoodsSold::class.java))
        }
    }

    override fun hasBought(userId: Long, goodsId: Long): PurchaseRecord? {
        val sql = "SELECT $FIELDS FROM ease_purchase_record WHERE user_id=:userId AND goods_id=:goodsId FETCH FIRST 1 ROWS ONLY"
        val params = HashMap<String, Any>(4)
        params["userId"] = userId
        params["goodsId"] = goodsId
        val res = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
        return if (!res.isEmpty()) {
            res[0]
        } else {
            null
        }
    }

    companion object {

        private val ROW_MAPPER = BeanPropertyRowMapper(PurchaseRecord::class.java)
        private val FIELDS = "id,user_id,goods_id,snap_goods_name,snap_price,snap_description,snap_detail,snap_pic_url,amount,purchase_time"
    }
}
