package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.PurchaseService
import com.ysmull.easemall.dao.CartDao
import com.ysmull.easemall.dao.GoodsDao
import com.ysmull.easemall.dao.PurchaseDao
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.PurchaseRecord
import com.ysmull.easemall.model.vo.ShopCartVO
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author maoyusu
 */
@Service
class PurchaseServiceImpl(
        internal var purchaseDao: PurchaseDao,
        internal var cartDao: CartDao,
        internal var goodsDao: GoodsDao
) : PurchaseService {


    @Async
    @Transactional(rollbackFor = [Exception::class])
    override fun buy(shopCarts: List<ShopCartVO>) {
        val goodsIds = shopCarts.map { it.goodsId }
        val goodsList = goodsDao.getGoodsByIds(goodsIds)
        val maps = goodsList.groupBy { it.id }
        val purchaseRecords = shopCarts.map { e ->
            val goods = maps.getValue(e.goodsId)[0]
            PurchaseRecord(
                    id=-1,
                    userId = e.userId,
                    goodsId = e.goodsId,
                    amount = e.amount,
                    purchaseTime = Date(System.currentTimeMillis()),
                    snapDescription = e.description,
                    snapDetail = goods.detail,
                    snapGoodsName = e.goodsName!!,
                    snapPicUrl = e.picUrl,
                    snapPrice = e.price
            )
        }
        purchaseDao.batchInsert(purchaseRecords)
        cartDao.batchDelete(shopCarts)
    }

    override fun getPurchaseHistory(userId: Long): List<PurchaseRecord> {
        return purchaseDao.getHistory(userId)
    }

    @Throws(RecordNotFoundException::class)
    override fun getPurchaseRecord(snapId: Long, userId: Long): PurchaseRecord {
        return purchaseDao.getPurchaseRecord(snapId, userId)
    }

    override fun hasBought(userId: Long, goodsId: Long): PurchaseRecord? {
        return purchaseDao.hasBought(userId, goodsId)
    }

}
