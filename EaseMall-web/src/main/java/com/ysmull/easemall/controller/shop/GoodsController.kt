package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.GoodsService
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.entity.Goods
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.model.vo.WebResponse
import com.ysmull.easemall.util.UserContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
class GoodsController(
        internal var goodsService: GoodsService
) {


    @GetMapping("/goods/all")
    @ResponseBody
    internal fun goodsAll(): WebResponse<List<Goods>> {
        val webResponse = WebResponse<List<Goods>>()
        webResponse.data = goodsService.getAllGoods()
        return webResponse
    }

    @GetMapping("/goods/published")
    @Privilege(role = [User.ROLE.SELLER])
    @ResponseBody
    internal fun goodsPublished(): WebResponse<List<Goods>> {
        val webResponse = WebResponse<List<Goods>>()
        val user = UserContext.currentUser
        webResponse.data = goodsService.getPublishedGoods(user.id!!)
        return webResponse
    }

    @GetMapping("/goods/{goodsId}")
    @ResponseBody
    @Throws(RecordNotFoundException::class)
    internal fun getGoods(@PathVariable("goodsId") goodsId: Long): WebResponse<Goods> {
        val webResponse = WebResponse<Goods>()
        val goods = goodsService.get(goodsId)
        webResponse.data = goods
        return webResponse
    }

    @DeleteMapping("/goods/delete/{goodsId}")
    @Privilege(role = [User.ROLE.SELLER])
    @ResponseBody
    internal fun delete(@PathVariable("goodsId") goodsId: Long): WebResponse<Void> {
        val webResponse = WebResponse<Void>()
        goodsService.delete(goodsId)
        return webResponse
    }
}
