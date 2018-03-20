package com.ysmull.easemall.dao;

import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.Goods;

import java.util.List;

/**
 * @author maoyusu
 */
public interface GoodsDao {

    /**
     * 获取所有商品
     *
     * @return List<Goods>
     */
    List<Goods> getAllGoods();

    /**
     * 获取publisher发布的所有商品
     *
     * @param publisher 发布者id
     * @return List<Goods>
     */
    List<Goods> getPublishedGoods(long publisher);


    /**
     * 获取指定ids的商品列表
     *
     * @param ids 商品id列表
     * @return List<Goods>
     */
    List<Goods> getGoodsByIds(List<Long> ids);


    /**
     * 获取指定id的商品
     *
     * @param goodsId 商品id
     * @return Goods
     */
    Goods get(long goodsId) throws RecordNotFoundException;

    /**
     * 添加商品，并返回新增商品的id
     *
     * @param goods 待添加的商品
     * @return 返回添加的商品的id
     */
    long insert(Goods goods);

    /**
     * 更新商品信息
     *
     * @param goods 待更新的商品
     */
    void update(Goods goods);


    /**
     * 删除商品，并没有真的删除，只是讲商品状态标记为不可用
     *
     * @param goodsId 商品id
     */
    void delete(long goodsId);

}
