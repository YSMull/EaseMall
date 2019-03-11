package com.ysmull.easemall.biz

import com.ysmull.easemall.model.vo.ShopCartVO

/**
 * @author maoyusu
 */
interface CartService {

    /**
     * 获取用户的购物车
     *
     * @param userId 用户id
     * @return 包含商品信息的ShopCartVO列表
     */
    fun getCart(userId: Long): List<ShopCartVO>

    /**
     * 添加商品至购物车
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  商品个数
     */
    fun addCart(userId: Long, goodsId: Long, amount: Long)
}
