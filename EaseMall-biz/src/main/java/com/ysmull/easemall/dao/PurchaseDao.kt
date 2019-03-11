package com.ysmull.easemall.dao

import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.GoodsSold
import com.ysmull.easemall.model.entity.PurchaseRecord

/**
 * @author maoyusu
 */
interface PurchaseDao {

    /**
     * 批量插入购买记录
     *
     * @param purchaseRecords 购买记录列表
     */
    fun batchInsert(purchaseRecords: List<PurchaseRecord>)

    /**
     * 获取用户的购买记录
     *
     * @param userId 用户id
     * @return 返回用户购买锅的所有商品记录
     */
    fun getHistory(userId: Long): List<PurchaseRecord>

    /**
     * 获取用户购买的商品的快照信息
     *
     * @param snapId 快照id
     * @param userId 用户id
     * @return PurchaseRecord
     */
    @Throws(RecordNotFoundException::class)
    fun getPurchaseRecord(snapId: Long, userId: Long): PurchaseRecord

    /**
     * 获取商品的销售情况
     *
     * @param goodsIds 商品id列表
     * @return 返回一组商品的销售情况
     */
    fun getSoldCount(goodsIds: List<Long>): List<GoodsSold>

    /**
     * 判断用户是否购买过某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 该商品的购买记录
     */
    fun hasBought(userId: Long, goodsId: Long): PurchaseRecord?
}
