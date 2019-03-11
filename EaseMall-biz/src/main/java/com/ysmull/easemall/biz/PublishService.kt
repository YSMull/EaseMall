package com.ysmull.easemall.biz

import com.ysmull.easemall.model.entity.Goods

/**
 * @author maoyusu
 */
interface PublishService {


    /**
     * 发布新商品
     *
     * @param goods 带发布的商品
     * @param picId 图片id
     * @return Goods
     */
    fun publishNew(goods: Goods, picId: String): Goods

    /**
     * 编辑已发布的商品的信息
     *
     * @param goods 修改后的的商品
     * @param picId 修改后的商品图片id
     * @return Goods
     */
    fun publishSave(goods: Goods, picId: String): Goods

}
