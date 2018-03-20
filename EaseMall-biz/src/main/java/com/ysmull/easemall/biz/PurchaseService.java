package com.ysmull.easemall.biz;

import com.ysmull.easemall.model.entity.PurchaseRecord;
import com.ysmull.easemall.model.vo.ShopCartVO;

import java.util.List;

/**
 * @author maoyusu
 */
public interface PurchaseService {

    /**
     * 购买商品
     * @param shopCarts 购物车列表
     */
    void buy(List<ShopCartVO> shopCarts);

    /**
     * 获取用户的所有购物记录
     * @param userId 用户id
     * @return 返回PurchaseRecord列表
     */
    List<PurchaseRecord> getPurchaseHistory(long userId);


    /**
     * 根据交易快照id获取用户某一次的购物记录
     * @param snapId 交易快照id
     * @param userId 用户id
     * @return 交易记录
     */
    PurchaseRecord getPurchaseRecord(long snapId, long userId);

    /**
     * 判断用户是否购买过某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 返回该商品的购买记录
     */
    PurchaseRecord hasBought(long userId, long goodsId);

}
