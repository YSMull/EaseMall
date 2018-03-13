package com.ysmull.easeshop.dao;

import com.ysmull.easeshop.model.entity.GoodsSold;
import com.ysmull.easeshop.model.entity.PurchaseRecord;

import java.util.List;

/**
 * @author maoyusu
 */
public interface PurchaseDao {

    /**
     * 批量插入购买记录
     *
     * @param purchaseRecords 购买记录列表
     */
    void batchInsert(List<PurchaseRecord> purchaseRecords);

    /**
     * 获取用户的购买记录
     *
     * @param userId 用户id
     * @return 返回用户购买锅的所有商品记录
     */
    List<PurchaseRecord> getHistory(long userId);

    /**
     * 获取用户购买的商品的快照信息
     *
     * @param snapId 快照id
     * @param userId 用户id
     * @return PurchaseRecord
     */
    PurchaseRecord getPurchaseRecord(long snapId, long userId);

    /**
     * 获取商品的销售情况
     *
     * @param goodsIds 商品id列表
     * @return 返回一组商品的销售情况
     */
    List<GoodsSold> getSoldCount(List<Long> goodsIds);

    /**
     * 判断用户是否购买过某商品
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 用户是否购买过该商品
     */
    boolean hasBought(long userId, long goodsId);
}
