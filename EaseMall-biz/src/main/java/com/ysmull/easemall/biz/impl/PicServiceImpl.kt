package com.ysmull.easemall.biz.impl

import com.ysmull.easemall.biz.PicService
import com.ysmull.easemall.dao.PicDao
import com.ysmull.easemall.util.ImageUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.UUID

/**
 * @author maoyusu
 */
@Service
class PicServiceImpl(
        internal var picDao: PicDao
) : PicService {


    @Throws(IOException::class)
    override fun savePic(pic: MultipartFile): String {
        val picId = UUID.randomUUID().toString()
        val picBytes = ImageUtil.getPicBytes(pic)
        picDao.savePic(pic.name, picId, picBytes)
        return picId
    }

    @Throws(Exception::class)
    override fun getPic(uuid: String, res: HttpServletResponse) {
        picDao.getPic(uuid, res)
    }
}
