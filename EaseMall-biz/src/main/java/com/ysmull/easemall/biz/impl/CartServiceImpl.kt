package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.CartService
import com.ysmull.easemall.dao.CartDao
import com.ysmull.easemall.dao.GoodsDao
import com.ysmull.easemall.model.entity.Goods
import com.ysmull.easemall.model.entity.ShopCart
import com.ysmull.easemall.model.vo.ShopCartVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors


/**
 * @author maoyusu
 */
@Service
class CartServiceImpl(
        internal var cartDao: CartDao,
        internal var goodsDao: GoodsDao
) : CartService {

    override fun getCart(userId: Long): List<ShopCartVO> {
        val shopCarts = cartDao.getCart(userId)
        val goodsIds = shopCarts.map { it.goodsId }
        val goodsList = goodsDao.getGoodsByIds(goodsIds)
        val maps = goodsList.groupBy { it.id }
        return shopCarts.map { e ->
            val goods = maps[e.goodsId]?.get(0)
            ShopCartVO(
                    userId = userId,
                    goodsId = e.goodsId,
                    amount = e.amount,
                    description = goods!!.description,
                    picUrl = goods.picUrl,
                    goodsName = goods.name,
                    price = goods.price
            )
        }
    }


    /**
     * 这里使用注解，并且shopCartDao.getCart()方法加上for update语句才可以实现顺序访问
     * 也可以考虑使用乐观锁
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [Exception::class])
    override fun addCart(userId: Long, goodsId: Long, amount: Long) {
        val shopCart = cartDao.getCart(userId, goodsId)
        if (shopCart == null) {
            cartDao.addCart(userId, goodsId, amount)
        } else {
            cartDao.changeCartAmount(userId, goodsId, amount)
        }
    }
}
