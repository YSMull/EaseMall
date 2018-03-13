package com.ysmull.easeshop.service;

import com.ysmull.easeshop.dao.GoodsDao;
import com.ysmull.easeshop.dao.PurchaseDao;
import com.ysmull.easeshop.model.entity.Goods;
import com.ysmull.easeshop.model.entity.GoodsSold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maoyusu
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    PurchaseService purchaseService;

    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

    public List<Goods> getPublishedGoods(long publisher) {
        List<Goods> goodsList = goodsDao.getPublishedGoods(publisher);
        List<Long> gid = goodsList.stream().map(Goods::getId).collect(Collectors.toList());
        List<GoodsSold> goodsSoldList = purchaseDao.getSoldCount(gid);
        goodsList.forEach(item -> {
            item.setHasSold(0L);
            goodsSoldList.forEach(sold -> {
                if (sold.getGoodsId().equals(item.getId())) {
                    item.setHasSold(sold.getSold());
                }
            });
        });
        return goodsList;
    }

    public List<Goods> getGoodsByIds(List<Long> ids) {
        return goodsDao.getGoodsByIds(ids);
    }

    public Goods get(long goodsId) {
        return goodsDao.get(goodsId);
    }

    public void delete(long goodsId) {
        goodsDao.delete(goodsId);
    }


}
