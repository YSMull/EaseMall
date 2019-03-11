package com.ysmull.easemall.model.entity

import com.ysmull.easemall.annotation.NoArg
import java.util.Date


/**
 * @author maoyusu
 */
@NoArg
data class ShopCart(
        var id: Long,
        var userId: Long,
        var goodsId: Long,
        var amount: Int,
        var status: String?,
        var createTime: Date?,
        var lastUpdateTime: Date?
)
