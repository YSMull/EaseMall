package com.ysmull.easemall.model.vo

/**
 * @author maoyusu
 */
class ShopCartVO(
        var userId: Long,
        var goodsId: Long,
        var goodsName: String?,
        var price: Float,
        var amount: Int,
        var picUrl: String?,
        var description: String?) {


    override fun toString(): String {
        return "ShopCartVO{" +
                "userId=" + userId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\''.toString() +
                ", picUrl='" + picUrl + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", price=" + price +
                ", amount=" + amount +
                '}'.toString()
    }
}
