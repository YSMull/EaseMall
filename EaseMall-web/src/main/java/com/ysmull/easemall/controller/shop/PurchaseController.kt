package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.PurchaseService
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.PurchaseRecord
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.model.vo.ShopCartVO
import com.ysmull.easemall.model.vo.WebResponse
import com.ysmull.easemall.util.UserContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
class PurchaseController(
        internal var purchaseService: PurchaseService
) {


    @PostMapping("buy")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    fun buy(@RequestBody carts: List<ShopCartVO>) {
        purchaseService.buy(carts)
    }

    @GetMapping("history")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    fun history(): WebResponse<List<PurchaseRecord>> {
        val webResponse = WebResponse<List<PurchaseRecord>>()
        val user = UserContext.currentUser
        webResponse.data = purchaseService.getPurchaseHistory(user.id)
        return webResponse
    }

    @GetMapping("snapshot/{snapId}")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    @Throws(RecordNotFoundException::class)
    fun snapshot(@PathVariable("snapId") snapId: Long?): WebResponse<PurchaseRecord> {
        val webResponse = WebResponse<PurchaseRecord>()
        val user = UserContext.currentUser
        webResponse.data = purchaseService.getPurchaseRecord(snapId!!, user.id)
        return webResponse
    }


    @GetMapping("hasbought")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    fun hasBought(@RequestParam("goodsId") goodsId: Long): WebResponse<PurchaseRecord> {
        val webResponse = WebResponse<PurchaseRecord>()
        val user = UserContext.currentUser
        webResponse.data = purchaseService.hasBought(user.id, goodsId)
        return webResponse
    }
}
