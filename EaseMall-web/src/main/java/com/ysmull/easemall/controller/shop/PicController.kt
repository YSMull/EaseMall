package com.ysmull.easemall.controller.shop

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.biz.PicService
import com.ysmull.easemall.model.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.net.URL
import java.net.URLConnection

/**
 * @author maoyusu
 */
@Controller
@RequestMapping("api")
class PicController {
    private val log = LoggerFactory.getLogger(PicController::class.java)
    @Autowired
    internal var picService: PicService? = null


    @GetMapping("/pic/{id}")
    @Throws(Exception::class)
    internal fun getPic(@PathVariable("id") uuid: String, res: HttpServletResponse) {
        picService!!.getPic(uuid, res)
    }

    @GetMapping("/pic/proxy")
    @Privilege(role = [User.ROLE.SELLER])
    @Throws(IOException::class)
    internal fun picProxy(@RequestParam("picUrl") picUrl: String, res: HttpServletResponse) {
        val pic = URL(picUrl)
        val conn = pic.openConnection()
        if (conn.contentLengthLong < MAXPICSIZE) {
            // 这里要设置content-length，否则递归调用该接口会无法正常返回
            res.setContentLengthLong(conn.contentLengthLong)
            FileCopyUtils.copy(conn.getInputStream(), res.outputStream)
        }

    }

    companion object {

        private val MAXPICSIZE = 3 * 1024 * 1000L
    }
}
