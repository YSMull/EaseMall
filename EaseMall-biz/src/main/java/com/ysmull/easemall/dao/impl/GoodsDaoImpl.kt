package com.ysmull.easemall.dao.impl

import com.ysmull.easemall.dao.GoodsDao
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.Goods
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import org.springframework.util.CollectionUtils

import java.util.Collections
import java.util.HashMap

/**
 * @author maoyusu
 */
@Repository
class GoodsDaoImpl(
        private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : GoodsDao {
 
    override val allGoods: List<Goods>
        get() {
            val sql = "SELECT $FIELDS FROM ease_goods_info WHERE status=1"
            val params = MapSqlParameterSource()
            return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
        }

    override fun getPublishedGoods(publisher: Long): List<Goods> {
        val sql = "SELECT $FIELDS FROM ease_goods_info WHERE publisher=:publisher AND status=1"
        val params = HashMap<String, Any>(2)
        params["publisher"] = publisher
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
    }

    override fun getGoodsByIds(ids: List<Long>): List<Goods> {
        if (CollectionUtils.isEmpty(ids)) {
            return emptyList()
        } else {
            val sql = "SELECT $FIELDS FROM ease_goods_info WHERE id IN (:ids)"
            val params = HashMap<String, Any>(2)
            params["ids"] = ids
            return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER)
        }
    }

    @Throws(RecordNotFoundException::class)
    override fun get(goodsId: Long): Goods? {
        val sql = "SELECT $FIELDS FROM ease_goods_info WHERE id =:goodsId AND status = 1"
        val params = HashMap<String, Any>(2)
        params["goodsId"] = goodsId
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, ROW_MAPPER)
        } catch (e: Exception) {
            throw RecordNotFoundException("no record")
        }

    }

    override fun insert(goods: Goods): Long {
        val sql = "INSERT INTO ease_goods_info(name, price, description, pic_url, detail, publisher) " + "VALUES(:name, :price, :description, :picUrl, :detail, :publisher)"
        val holder = GeneratedKeyHolder()
        val params = MapSqlParameterSource()
        params.addValue("name", goods.name)
        params.addValue("price", goods.price)
        params.addValue("description", goods.description)
        params.addValue("picUrl", goods.picUrl)
        params.addValue("detail", goods.detail)
        params.addValue("publisher", goods.publisher)
        namedParameterJdbcTemplate.update(sql, params, holder)
        return holder.key!!.toLong()
    }

    override fun update(goods: Goods) {
        val sql = "UPDATE ease_goods_info SET name=:name, price=:price, description=:description, pic_url=:picUrl, detail=:detail, publisher=:publisher" + " WHERE id=:id"
        val holder = GeneratedKeyHolder()
        val params = MapSqlParameterSource()
        params.addValue("id", goods.id)
        params.addValue("name", goods.name)
        params.addValue("price", goods.price)
        params.addValue("description", goods.description)
        params.addValue("picUrl", goods.picUrl)
        params.addValue("detail", goods.detail)
        params.addValue("publisher", goods.publisher)
        namedParameterJdbcTemplate.update(sql, params, holder)

    }

    override fun delete(goodsId: Long) {
        val sql = "UPDATE ease_goods_info SET status=0 WHERE id=:goodsId"
        val params = MapSqlParameterSource()
        params.addValue("goodsId", goodsId)
        namedParameterJdbcTemplate.update(sql, params)
    }

    companion object {

        private val ROW_MAPPER = BeanPropertyRowMapper(Goods::class.java)
        private val FIELDS = "id,name,price,description,detail,pic_url,publisher,status"
    }


}
