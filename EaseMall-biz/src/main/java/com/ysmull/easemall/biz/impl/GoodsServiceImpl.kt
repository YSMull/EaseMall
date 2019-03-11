package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.GoodsService
import com.ysmull.easemall.biz.PurchaseService
import com.ysmull.easemall.dao.GoodsDao
import com.ysmull.easemall.dao.PurchaseDao
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.Goods
import com.ysmull.easemall.model.entity.GoodsSold
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * @author maoyusu
 */
@Service
class GoodsServiceImpl(
        internal var goodsDao: GoodsDao,
        internal var purchaseDao: PurchaseDao,
        internal var purchaseService: PurchaseService
) : GoodsService {

    override fun getAllGoods(): List<Goods> {
        return goodsDao.allGoods
    }

    override fun getPublishedGoods(publisher: Long): List<Goods> {
        val goodsList = goodsDao.getPublishedGoods(publisher)
        val gid = goodsList.map { it.id }
        val goodsSoldList = purchaseDao.getSoldCount(gid)
        goodsList.forEach { item ->
            item.hasSold = 0L
            goodsSoldList.forEach { sold ->
                if (sold.goodsId == item.id) {
                    item.hasSold = sold.sold
                }
            }
        }
        return goodsList
    }

    @Throws(RecordNotFoundException::class)
    override fun get(goodsId: Long): Goods? {
        return goodsDao.get(goodsId)
    }

    override fun delete(goodsId: Long) {
        goodsDao.delete(goodsId)
    }


}
