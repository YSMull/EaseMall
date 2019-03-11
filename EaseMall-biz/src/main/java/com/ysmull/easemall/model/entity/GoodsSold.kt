package com.ysmull.easemall.model.entity

import com.ysmull.easemall.annotation.NoArg

/**
 * @author maoyusu
 */
@NoArg
data class GoodsSold(
        var goodsId: Long,
        var sold: Long
)
