package com.ysmull.easemall.dao

import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.Goods

/**
 * @author maoyusu
 */
interface GoodsDao {

    /**
     * 获取所有商品
     *
     * @return List<Goods>
    </Goods> */
    val allGoods: List<Goods>

    /**
     * 获取publisher发布的所有商品
     *
     * @param publisher 发布者id
     * @return List<Goods>
    </Goods> */
    fun getPublishedGoods(publisher: Long): List<Goods>


    /**
     * 获取指定ids的商品列表
     *
     * @param ids 商品id列表
     * @return List<Goods>
    </Goods> */
    fun getGoodsByIds(ids: List<Long>): List<Goods>


    /**
     * 获取指定id的商品
     *
     * @param goodsId 商品id
     * @return Goods
     */
    @Throws(RecordNotFoundException::class)
    operator fun get(goodsId: Long): Goods?

    /**
     * 添加商品，并返回新增商品的id
     *
     * @param goods 待添加的商品
     * @return 返回添加的商品的id
     */
    fun insert(goods: Goods): Long

    /**
     * 更新商品信息
     *
     * @param goods 待更新的商品
     */
    fun update(goods: Goods)


    /**
     * 删除商品，并没有真的删除，只是讲商品状态标记为不可用
     *
     * @param goodsId 商品id
     */
    fun delete(goodsId: Long)

}
