package com.ysmull.easemall.controller.shop;

import com.ysmull.easemall.annotation.Privilege;
import com.ysmull.easemall.biz.GoodsService;
import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.Goods;
import com.ysmull.easemall.model.entity.User;
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
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("/goods/all")
    @ResponseBody
    WebResponse<List<Goods>> goodsAll() {
        WebResponse<List<Goods>> webResponse = new WebResponse<>();
        webResponse.setData(goodsService.getAllGoods());
        return webResponse;
    }

    @GetMapping("/goods/published")
    @Privilege(role = User.ROLE.SELLER)
    @ResponseBody
    WebResponse<List<Goods>> goodsPublished() {
        WebResponse<List<Goods>> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(goodsService.getPublishedGoods(user.getId()));
        return webResponse;
    }

    @GetMapping("/goods/{goodsId}")
    @ResponseBody
    WebResponse<Goods> getGoods(@PathVariable("goodsId") long goodsId) throws RecordNotFoundException {
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
