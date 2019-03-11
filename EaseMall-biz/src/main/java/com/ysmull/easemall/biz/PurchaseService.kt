package com.ysmull.easemall.biz

import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.PurchaseRecord
import com.ysmull.easemall.model.vo.ShopCartVO

/**
 * @author maoyusu
 */
interface PurchaseService {

    /**
     * 购买商品
     *
     * @param shopCarts 购物车列表
     */
    fun buy(shopCarts: List<ShopCartVO>)

    /**
     * 获取用户的所有购物记录
     *
     * @param userId 用户id
     * @return 返回PurchaseRecord列表
     */
    fun getPurchaseHistory(userId: Long): List<PurchaseRecord>


    /**
     * 根据交易快照id获取用户某一次的购物记录
     *
     * @param snapId 交易快照id
     * @param userId 用户id
     * @return 交易记录
     */
    @Throws(RecordNotFoundException::class)
    fun getPurchaseRecord(snapId: Long, userId: Long): PurchaseRecord

    /**
     * 判断用户是否购买过某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 返回该商品的购买记录
     */
    fun hasBought(userId: Long, goodsId: Long): PurchaseRecord?

}
