package com.ysmull.easeshop.service;

import com.ysmull.easeshop.dao.PicDao;
import com.ysmull.easeshop.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author maoyusu
 */
@Service
public class PicService {

    @Autowired
    PicDao picDao;

    public String savePic(MultipartFile pic) throws IOException {
        String picId = UUID.randomUUID().toString();
        byte[] picBytes = ImageUtil.getPicBytes(pic);
        picDao.savePic(pic.getName(), picId, picBytes);
        return picId;
    }
}
