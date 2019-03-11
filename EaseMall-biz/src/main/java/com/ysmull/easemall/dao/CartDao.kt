package com.ysmull.easemall.dao

import com.ysmull.easemall.model.entity.ShopCart
import com.ysmull.easemall.model.vo.ShopCartVO

/**
 * @author maoyusu
 */
interface CartDao {

    /**
     * 获取用户的购物车
     *
     * @param userId 用户id
     * @return List<ShopCart>
    </ShopCart> */
    fun getCart(userId: Long): List<ShopCart>

    /**
     * 判断某商品是否在该用户的购物车里
     * 该查询有排他锁
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 如果商品在该用户的购物车中，则返回这个商品，否则返回`null`
     */
    fun getCart(userId: Long, goodsId: Long): ShopCart?

    /**
     * 增加商品到购物车
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  待购买数量
     */
    fun addCart(userId: Long, goodsId: Long, amount: Long)


    /**
     * 修改购物车中某商品的购买数量
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  修改后的购买数量
     */
    fun changeCartAmount(userId: Long, goodsId: Long, amount: Long)

    /**
     * 批量删除购物车中的商品
     *
     * @param shopCartVOs 商品列表
     */
    fun batchDelete(shopCartVOs: List<ShopCartVO>)

    /**
     * 从购物车中删除某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     */
    fun delete(userId: Long, goodsId: Long)
}
