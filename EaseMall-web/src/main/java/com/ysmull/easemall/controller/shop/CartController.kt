package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.CartService
import com.ysmull.easemall.dao.CartDao
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
class CartController(
        private val cartService: CartService,
        private val cartDao: CartDao
) {

    @GetMapping("/getcart")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    internal fun getCart(): WebResponse<List<ShopCartVO>> {
        val webResponse = WebResponse<List<ShopCartVO>>()
        val user = UserContext.currentUser
        val shopCarts = cartService.getCart(user.id)
        webResponse.data = shopCarts
        return webResponse
    }

    @PostMapping("/addcart")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    internal fun addCart(@RequestBody shopCartVO: ShopCartVO) {
        cartService.addCart(shopCartVO.userId, shopCartVO.goodsId, shopCartVO.amount.toLong())
    }

    @PostMapping("/changecart")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    internal fun changeCart(@RequestBody shopCartVO: ShopCartVO) {
        cartDao.changeCartAmount(shopCartVO.userId, shopCartVO.goodsId, shopCartVO.amount.toLong())
    }

    @PostMapping("/deletecart")
    @Privilege(role = [User.ROLE.BUYER])
    @ResponseBody
    internal fun deleteCart(@RequestBody shopCartVO: ShopCartVO) {
        cartDao.delete(shopCartVO.userId, shopCartVO.goodsId)
    }

}
