package com.ysmull.easeshop.controller.shop;

import com.ysmull.easeshop.dao.ShopCartDao;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.ShopCartVO;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.CartService;
import com.ysmull.easeshop.util.UserContext;
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
    private ShopCartDao shopCartDao;

    @GetMapping("/getcart")
    @ResponseBody
    WebResponse<List<ShopCartVO>> getcart() {
        WebResponse<List<ShopCartVO>> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        List<ShopCartVO> shopCarts = cartService.getCart(user.getId());
        webResponse.setData(shopCarts);
        return webResponse;
    }

    @PostMapping("/addcart")
    @ResponseBody
    void addcart(@RequestBody ShopCartVO shopCartVO) {
        cartService.addCart(shopCartVO.getUserId(), shopCartVO.getGoodsId(), shopCartVO.getAmount());
    }

    @PostMapping("/changecart")
    @ResponseBody
    void changecart(@RequestBody ShopCartVO shopCartVO) {
        shopCartDao.changeCartAmount(shopCartVO.getUserId(), shopCartVO.getGoodsId(), shopCartVO.getAmount());
    }

    @PostMapping("/deletecart")
    @ResponseBody
    void deletecart(@RequestBody ShopCartVO shopCartVO) {
        shopCartDao.delete(shopCartVO.getUserId(), shopCartVO.getGoodsId());
    }

}
