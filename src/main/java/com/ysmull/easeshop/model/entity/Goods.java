package com.ysmull.easeshop.model.entity;

/**
 * @author maoyusu
 */
public class Goods {
    private Long id;
    private String name;
    private Float price;
    private String description;
    private String detail;
    private String picUrl;
    private Long publisher;
    private Long hasSold;

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

    public Long getPublisher() {
        return publisher;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }

    public Long getHasSold() {
        return hasSold;
    }

    public void setHasSold(Long hasSold) {
        this.hasSold = hasSold;
    }
}
