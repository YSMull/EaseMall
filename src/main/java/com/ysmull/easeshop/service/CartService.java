package com.ysmull.easeshop.service;

import com.ysmull.easeshop.dao.GoodsDao;
import com.ysmull.easeshop.dao.ShopCartDao;
import com.ysmull.easeshop.model.entity.Goods;
import com.ysmull.easeshop.model.entity.ShopCart;
import com.ysmull.easeshop.model.vo.ShopCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author maoyusu
 */
@Service
public class CartService {

    @Autowired
    ShopCartDao shopCartDao;

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    public List<ShopCartVO> getCart(long userId) {
        List<ShopCart> shopCarts = shopCartDao.getCart(userId);
        List<Long> goodsIds = shopCarts.stream().map(ShopCart::getGoodsId)
                .collect(Collectors.toList());
        List<Goods> goodsList = goodsDao.getGoodsByIds(goodsIds);
        Map<Long, List<Goods>> maps = goodsList.stream()
                .collect(Collectors.groupingBy(Goods::getId));
        return shopCarts.stream().map(e -> {
            ShopCartVO shopCartVO = new ShopCartVO();
            shopCartVO.setUserId(userId);
            shopCartVO.setGoodsId(e.getGoodsId());
            shopCartVO.setAmount(e.getAmount());
            Goods goods = maps.get(e.getGoodsId()).get(0);
            shopCartVO.setDescription(goods.getDescription());
            shopCartVO.setPicUrl(goods.getPicUrl());
            shopCartVO.setGoodsName(goods.getName());
            shopCartVO.setPrice(goods.getPrice());
            return shopCartVO;
        }).collect(Collectors.toList());
    }


    /**
     * 这里使用注解，并且shopCartDao.getCart()方法加上for update语句才可以实现顺序访问
     * 也可以考虑使用乐观锁
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void addCart(long userId, long goodsId, long amount) {
        ShopCart shopCart = shopCartDao.getCart(userId, goodsId);
        if (shopCart == null) {
            shopCartDao.addCart(userId, goodsId, amount);
        } else {
            shopCartDao.changeCartAmount(userId, goodsId, amount);
        }
    }
}
