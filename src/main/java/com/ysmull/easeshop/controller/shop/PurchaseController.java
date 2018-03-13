package com.ysmull.easeshop.controller.shop;

import com.ysmull.easeshop.model.entity.PurchaseRecord;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.ShopCartVO;
import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.service.PurchaseService;
import com.ysmull.easeshop.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("buy")
    @ResponseBody
    public void buy(@RequestBody List<ShopCartVO> carts) {
        purchaseService.buy(carts);
    }

    @GetMapping("history")
    @ResponseBody
    public WebResponse<List<PurchaseRecord>> history() {
        WebResponse<List<PurchaseRecord>> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.getPurchaseHistory(user.getId()));
        return webResponse;
    }

    @GetMapping("snapshot/{snapId}")
    @ResponseBody
    public WebResponse<PurchaseRecord> snapshot(@PathVariable("snapId") Long snapId) {
        WebResponse<PurchaseRecord> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.getPurchaseRecord(snapId, user.getId()));
        return webResponse;
    }


    @GetMapping("hasbought")
    @ResponseBody
    public WebResponse<Boolean> hasBought(@RequestParam long goodsId) {
        WebResponse<Boolean> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.hasBought(user.getId(), goodsId));
        return webResponse;
    }
}
