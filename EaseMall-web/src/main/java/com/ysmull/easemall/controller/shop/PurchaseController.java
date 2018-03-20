package com.ysmull.easemall.controller.shop;

import com.ysmull.easemall.annotation.Privilege;
import com.ysmull.easemall.biz.PurchaseService;
import com.ysmull.easemall.exception.RecordNotFoundException;
import com.ysmull.easemall.model.entity.PurchaseRecord;
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
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("buy")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    public void buy(@RequestBody List<ShopCartVO> carts) {
        purchaseService.buy(carts);
    }

    @GetMapping("history")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    public WebResponse<List<PurchaseRecord>> history() {
        WebResponse<List<PurchaseRecord>> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.getPurchaseHistory(user.getId()));
        return webResponse;
    }

    @GetMapping("snapshot/{snapId}")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    public WebResponse<PurchaseRecord> snapshot(@PathVariable("snapId") Long snapId) throws RecordNotFoundException {
        WebResponse<PurchaseRecord> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.getPurchaseRecord(snapId, user.getId()));
        return webResponse;
    }


    @GetMapping("hasbought")
    @Privilege(role = User.ROLE.BUYER)
    @ResponseBody
    public WebResponse<PurchaseRecord> hasBought(@RequestParam("goodsId") long goodsId) {
        WebResponse<PurchaseRecord> webResponse = new WebResponse<>();
        User user = UserContext.getCurrentUser();
        webResponse.setData(purchaseService.hasBought(user.getId(), goodsId));
        return webResponse;
    }
}
