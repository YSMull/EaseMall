package com.ysmull.easemall.biz

import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.Goods

/**
 * @author maoyusu
 */
interface GoodsService {

    /**
     * 获取所有商品
     *
     * @return List<Goods>
    </Goods> */
    fun getAllGoods(): List<Goods>

    /**
     * 获取publisher发布的所有商品，包含每个商品的卖出数
     *
     * @param publisher 发布者id
     * @return List<Goods>
    </Goods> */
    fun getPublishedGoods(publisher: Long): List<Goods>

    /**
     * 获取指定id的商品
     *
     * @param goodsId 商品id
     * @return Goods
     */
    @Throws(RecordNotFoundException::class)
    operator fun get(goodsId: Long): Goods?

    /**
     * 删除商品，并没有真的删除，只是讲商品状态标记为不可用
     *
     * @param goodsId 商品id
     */
    fun delete(goodsId: Long)
}
