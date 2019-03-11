package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.PicService
import com.ysmull.easemall.biz.PublishService
import com.ysmull.easemall.model.entity.Goods
import com.ysmull.easemall.model.entity.User
import com.ysmull.easemall.model.vo.WebResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import java.io.IOException

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/api")
class PublishController {

    @Autowired
    internal var publishService: PublishService? = null

    @Autowired
    internal var picService: PicService? = null

    /**
     * tips: 这里的Goods直接从formData中解析
     */
    @PostMapping("/publish/new")
    @Privilege(role = [User.ROLE.SELLER])
    @ResponseBody
    @Throws(IOException::class)
    internal fun publishNew(@RequestParam("file") pic: MultipartFile, goods: Goods): WebResponse<Goods> {
        val webResponse = WebResponse<Goods>()
        val picId = picService!!.savePic(pic)
        val newGoods = publishService!!.publishNew(goods, picId)
        webResponse.data = newGoods
        return webResponse
    }

    @PostMapping("/publish/save")
    @Privilege(role = [User.ROLE.SELLER])
    @ResponseBody
    @Throws(IOException::class)
    internal fun publishSave(@RequestParam("file") pic: MultipartFile, goods: Goods): WebResponse<Goods> {
        val webResponse = WebResponse<Goods>()
        val picId = picService!!.savePic(pic)
        val newGoods = publishService!!.publishSave(goods, picId)
        webResponse.data = newGoods
        return webResponse
    }


}
