package com.ysmull.easemall.biz.impl;

import com.ysmull.easemall.biz.PicService;
import com.ysmull.easemall.dao.PicDao;
import com.ysmull.easemall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author maoyusu
 */
@Service
public class PicServiceImpl implements PicService {

    @Autowired
    PicDao picDao;

    @Override
    public String savePic(MultipartFile pic) throws IOException {
        String picId = UUID.randomUUID().toString();
        byte[] picBytes = ImageUtil.getPicBytes(pic);
        picDao.savePic(pic.getName(), picId, picBytes);
        return picId;
    }

    @Override
    public void getPic(String uuid, HttpServletResponse res) throws Exception {
        picDao.getPic(uuid, res);
    }
}
