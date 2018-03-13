package com.ysmull.easeshop.service;

import com.ysmull.easeshop.dao.GoodsDao;
import com.ysmull.easeshop.model.entity.Goods;
import com.ysmull.easeshop.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maoyusu
 */
@Service
public class PublishService {

    @Autowired
    GoodsDao goodsDao;

    public Goods publishNew(Goods goods, String picId) {
        goods.setPublisher(UserContext.getCurrentUser().getId());
        goods.setPicUrl("/api/pic/" + picId);
        long newGoodsId = goodsDao.insert(goods);
        goods.setId(newGoodsId);
        return goods;
    }

    public Goods publishSave(Goods goods, String picId) {
        goods.setPublisher(UserContext.getCurrentUser().getId());
        goods.setPicUrl("/api/pic/" + picId);
        goodsDao.update(goods);
        return goods;
    }
}
