package com.ysmull.easemall.model.entity

import com.ysmull.easemall.annotation.NoArg

/**
 * @author maoyusu
 */
@NoArg
data class Goods(
        var id: Long,
        var name: String,
        var price: Float,
        var description: String,
        var detail: String,
        var picUrl: String,
        var publisher: Long,
        var hasSold: Long
)
