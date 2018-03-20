package com.ysmull.easemall.biz.impl;

import com.ysmull.easemall.biz.PublishService;
import com.ysmull.easemall.dao.GoodsDao;
import com.ysmull.easemall.model.entity.Goods;
import com.ysmull.easemall.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maoyusu
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    GoodsDao goodsDao;

    @Override
    public Goods publishNew(Goods goods, String picId) {
        goods.setPublisher(UserContext.getCurrentUser().getId());
        goods.setPicUrl("/api/pic/" + picId);
        long newGoodsId = goodsDao.insert(goods);
        goods.setId(newGoodsId);
        return goods;
    }

    @Override
    public Goods publishSave(Goods goods, String picId) {
        goods.setPublisher(UserContext.getCurrentUser().getId());
        goods.setPicUrl("/api/pic/" + picId);
        goodsDao.update(goods);
        return goods;
    }
}
