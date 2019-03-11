package com.ysmull.easemall.model.vo

import com.ysmull.easemall.model.entity.Goods

/**
 * @author maoyusu
 */
class GoodsVO(goods: Goods) {

    var id: Long? = null
    var name: String? = null
    var price: Float? = null
    var description: String? = null
    var detail: String? = null
    var picUrl: String? = null

    var curUser: String? = null
    var buy: Boolean? = null


    init {
        this.id = goods.id
        this.name = goods.name
        this.price = goods.price
        this.description = goods.description
        this.detail = goods.detail
        this.picUrl = goods.picUrl
    }

    override fun toString(): String {
        return "GoodsVO{" +
                "id=" + id +
                ", name='" + name + '\''.toString() +
                ", price=" + price +
                ", description='" + description + '\''.toString() +
                ", detail='" + detail + '\''.toString() +
                ", picUrl='" + picUrl + '\''.toString() +
                ", curUser='" + curUser + '\''.toString() +
                ", buy=" + buy +
                '}'.toString()
    }
}
