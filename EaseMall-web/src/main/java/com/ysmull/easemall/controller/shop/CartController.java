package com.ysmull.easemall.controller.shop;

import com.ysmull.easemall.annotation.Privilege;
import com.ysmull.easemall.biz.CartService;
import com.ysmull.easemall.dao.CartDao;
import com.ysmull.easemall.model.entity.User;
import com.ysmull.easemall.model.vo.ShopCartVO;
import com.ysmull.easemall.model.vo.WebResponse;
import com.ysmull.easemall.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDao cartDao;

    @GetMapping("/getcart")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    WebResponse<List<ShopCartVO>> getcart() {
        WebResponse<List<ShopCartVO>> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        List<ShopCartVO> shopCarts = cartService.getCart(user.getId());
        webResponse.setData(shopCarts);
        return webResponse;
    }

    @PostMapping("/addcart")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    void addcart(@RequestBody ShopCartVO shopCartVO) {
        cartService.addCart(shopCartVO.getUserId(), shopCartVO.getGoodsId(), shopCartVO.getAmount());
    }

    @PostMapping("/changecart")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    void changecart(@RequestBody ShopCartVO shopCartVO) {
        cartDao.changeCartAmount(shopCartVO.getUserId(), shopCartVO.getGoodsId(), shopCartVO.getAmount());
    }

    @PostMapping("/deletecart")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    void deletecart(@RequestBody ShopCartVO shopCartVO) {
        cartDao.delete(shopCartVO.getUserId(), shopCartVO.getGoodsId());
    }

}
