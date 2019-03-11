package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.PublishService
import com.ysmull.easemall.dao.GoodsDao
import com.ysmull.easemall.model.entity.Goods
import com.ysmull.easemall.util.UserContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author maoyusu
 */
@Service
class PublishServiceImpl(
        internal var goodsDao: GoodsDao
) : PublishService {

    override fun publishNew(goods: Goods, picId: String): Goods {
        goods.publisher = UserContext.currentUser!!.id
        goods.picUrl = "/api/pic/$picId"
        val newGoodsId = goodsDao.insert(goods)
        goods.id = newGoodsId
        return goods
    }

    override fun publishSave(goods: Goods, picId: String): Goods {
        goods.publisher = UserContext.currentUser!!.id
        goods.picUrl = "/api/pic/$picId"
        goodsDao.update(goods)
        return goods
    }
}
