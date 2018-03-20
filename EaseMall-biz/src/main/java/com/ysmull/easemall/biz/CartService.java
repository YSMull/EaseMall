package com.ysmull.easemall.biz;

import com.ysmull.easemall.model.vo.ShopCartVO;

import java.util.List;

/**
 * @author maoyusu
 */
public interface CartService {

    /**
     * 获取用户的购物车
     *
     * @param userId 用户id
     * @return 包含商品信息的ShopCartVO列表
     */
    List<ShopCartVO> getCart(long userId);

    /**
     * 添加商品至购物车
     *
     * @param userId 用户id
     * @param goodsId 商品id
     * @param amount 商品个数
     */
    void addCart(long userId, long goodsId, long amount);
}
