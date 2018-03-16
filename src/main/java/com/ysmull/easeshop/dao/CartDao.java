package com.ysmull.easeshop.dao;

import com.ysmull.easeshop.model.entity.ShopCart;
import com.ysmull.easeshop.model.vo.ShopCartVO;

import java.util.List;

/**
 * @author maoyusu
 */
public interface CartDao {

    /**
     * 获取用户的购物车
     *
     * @param userId 用户id
     * @return List<ShopCart>
     */
    List<ShopCart> getCart(long userId);

    /**
     * 判断某商品是否在该用户的购物车里
     * 该查询有排他锁
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 如果商品在该用户的购物车中，则返回这个商品，否则返回{@code null}
     */
    ShopCart getCart(long userId, long goodsId);

    /**
     * 增加商品到购物车
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  待购买数量
     */
    void addCart(long userId, long goodsId, long amount);


    /**
     * 修改购物车中某商品的购买数量
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  修改后的购买数量
     */
    void changeCartAmount(long userId, long goodsId, long amount);

    /**
     * 批量删除购物车中的商品
     *
     * @param shopCartVOs 商品列表
     */
    void batchDelete(List<ShopCartVO> shopCartVOs);

    /**
     * 从购物车中删除某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     */
    void delete(long userId, long goodsId);
}
