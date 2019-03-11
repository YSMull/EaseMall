package com.ysmull.easemall.model.entity


import com.ysmull.easemall.annotation.NoArg
import java.util.Date

/**
 * @author maoyusu
 */
@NoArg
data class PurchaseRecord(
        var id: Long,
        var userId: Long,
        var goodsId: Long,
        var snapGoodsName: String,
        var snapDetail: String,
        var snapPicUrl: String?,
        var snapDescription: String?,
        var snapPrice: Float,
        var amount: Int,
        var purchaseTime: Date
)
