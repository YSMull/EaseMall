package com.ysmull.easemall.biz.impl;

import com.ysmull.easemall.biz.GoodsService;
import com.ysmull.easemall.biz.PurchaseService;
import com.ysmull.easemall.dao.GoodsDao;
import com.ysmull.easemall.dao.PurchaseDao;
import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.Goods;
import com.ysmull.easemall.model.entity.GoodsSold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maoyusu
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    PurchaseService purchaseService;

    @Override
    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

    @Override
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

    @Override
    public Goods get(long goodsId) throws RecordNotFoundException {
        return goodsDao.get(goodsId);
    }

    @Override
    public void delete(long goodsId) {
        goodsDao.delete(goodsId);
    }


}
