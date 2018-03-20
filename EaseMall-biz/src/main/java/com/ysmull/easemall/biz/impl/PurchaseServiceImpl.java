package com.ysmull.easemall.biz.impl;

import com.ysmull.easemall.biz.PurchaseService;
import com.ysmull.easemall.dao.CartDao;
import com.ysmull.easemall.dao.PurchaseDao;
import com.ysmull.easemall.model.entity.PurchaseRecord;
import com.ysmull.easemall.model.vo.ShopCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maoyusu
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    CartDao cartDao;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buy(List<ShopCartVO> shopCarts) {
        List<PurchaseRecord> purchaseRecords = shopCarts.stream().map(e -> {
            PurchaseRecord r = new PurchaseRecord();
            r.setUserId(e.getUserId());
            r.setGoodsId(e.getGoodsId());
            r.setAmount(e.getAmount());
            r.setPurchaseTime(new Date(System.currentTimeMillis()));
            r.setSnapDescription(e.getDescription());
            r.setSnapDetail("");
            r.setSnapGoodsName(e.getGoodsName());
            r.setSnapPicUrl(e.getPicUrl());
            r.setSnapPrice(e.getPrice());
            return r;
        }).collect(Collectors.toList());
        purchaseDao.batchInsert(purchaseRecords);
        cartDao.batchDelete(shopCarts);
    }

    @Override
    public List<PurchaseRecord> getPurchaseHistory(long userId) {
        return purchaseDao.getHistory(userId);
    }

    @Override
    public PurchaseRecord getPurchaseRecord(long snapId, long userId) {
        return purchaseDao.getPurchaseRecord(snapId, userId);
    }

    @Override
    public PurchaseRecord hasBought(long userId, long goodsId) {
        return purchaseDao.hasBought(userId, goodsId);
    }

}
