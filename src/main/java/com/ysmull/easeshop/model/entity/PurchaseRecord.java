package com.ysmull.easeshop.model.entity;


import java.util.Date;

/**
 * @author maoyusu
 */
public class PurchaseRecord {
    private long id;
    private long userId;
    private long goodsId;
    private String snapGoodsName;
    private String snapDescription;
    private String snapDetail;
    private String snapPicUrl;
    private float snapPrice;
    private int amount;
    private Date purchaseTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSnapGoodsName() {
        return snapGoodsName;
    }

    public void setSnapGoodsName(String snapGoodsName) {
        this.snapGoodsName = snapGoodsName;
    }

    public String getSnapDescription() {
        return snapDescription;
    }

    public void setSnapDescription(String snapDescription) {
        this.snapDescription = snapDescription;
    }

    public String getSnapDetail() {
        return snapDetail;
    }

    public void setSnapDetail(String snapDetail) {
        this.snapDetail = snapDetail;
    }

    public String getSnapPicUrl() {
        return snapPicUrl;
    }

    public void setSnapPicUrl(String snapPicUrl) {
        this.snapPicUrl = snapPicUrl;
    }

    public float getSnapPrice() {
        return snapPrice;
    }

    public void setSnapPrice(float snapPrice) {
        this.snapPrice = snapPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
