package com.ysmull.easemall.biz;

import com.ysmull.easemall.model.entity.Goods;

import java.util.List;

/**
 * @author maoyusu
 */
public interface GoodsService {

    /**
     * 获取所有商品
     *
     * @return List<Goods>
     */
    List<Goods> getAllGoods();

    /**
     * 获取publisher发布的所有商品，包含每个商品的卖出数
     *
     * @param publisher 发布者id
     * @return List<Goods>
     */
    List<Goods> getPublishedGoods(long publisher);

    /**
     * 获取指定id的商品
     *
     * @param goodsId 商品id
     * @return Goods
     */
    Goods get(long goodsId);

    /**
     * 删除商品，并没有真的删除，只是讲商品状态标记为不可用
     *
     * @param goodsId 商品id
     */
    void delete(long goodsId);
}
