package com.ysmull.easeshop.dao;

/**
 * @author maoyusu
 */
public interface PicDao {

    /**
     * 新增图片进数据库
     *
     * @param picName  图片名称
     * @param picId    图片id
     * @param picBytes 图片内容
     */
    void savePic(String picName, String picId, byte[] picBytes);

}
