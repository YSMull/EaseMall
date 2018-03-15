package com.ysmull.easeshop.controller.shop;

import com.ysmull.easeshop.annotation.Privilege;
import com.ysmull.easeshop.model.entity.Goods;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.GoodsService;
import com.ysmull.easeshop.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("/goods")
    @ResponseBody
    List<Goods> goodsList() {
        User user = UserContext.getCurrentUser();
        if (user != null && user.getRole() == User.ROLE.SELLER) {
            return goodsService.getPublishedGoods(user.getId());
        } else {
            return goodsService.getAllGoods();
        }
    }

    @GetMapping("/goods/{goodsId}")
    @ResponseBody
    WebResponse<Goods> getGoods(@PathVariable("goodsId") long goodsId) {
        WebResponse<Goods> webResponse = new WebResponse<>();
        Goods goods = goodsService.get(goodsId);
        webResponse.setData(goods);
        return webResponse;
    }

    @DeleteMapping("/goods/delete/{goodsId}")
    @Privilege(role = User.ROLE.SELLER)
    @ResponseBody
    WebResponse<Void> delete(@PathVariable("goodsId") long goodsId) {
        WebResponse<Void> webResponse = new WebResponse<>();
        goodsService.delete(goodsId);
        return webResponse;
    }
}
