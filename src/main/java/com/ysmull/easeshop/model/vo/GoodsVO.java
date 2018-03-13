package com.ysmull.easeshop.model.vo;

import com.ysmull.easeshop.model.entity.Goods;

/**
 * @author maoyusu
 */
public class GoodsVO {

    private Long id;
    private String name;
    private Float price;
    private String description;
    private String detail;
    private String picUrl;

    private String curUser;
    private Boolean buy;


    public GoodsVO(Goods goods) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.price = goods.getPrice();
        this.description = goods.getDescription();
        this.detail = goods.getDetail();
        this.picUrl = goods.getPicUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCurUser() {
        return curUser;
    }

    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    @Override
    public String toString() {
        return "GoodsVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", detail='" + detail + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", curUser='" + curUser + '\'' +
                ", buy=" + buy +
                '}';
    }
}
