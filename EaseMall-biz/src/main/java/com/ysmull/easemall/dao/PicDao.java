package com.ysmull.easemall.dao;

import com.ysmull.easemall.exception.PicNotFoundException;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 把图片写入Http输出
     *
     * @param uuid 图片id
     * @param res  HttpServletResponse
     * @throws PicNotFoundException
     */
    void getPic(String uuid, HttpServletResponse res) throws PicNotFoundException;

}
