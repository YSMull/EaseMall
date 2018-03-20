package com.ysmull.easemall.controller.shop;

import com.ysmull.easemall.annotation.Privilege;
import com.ysmull.easemall.biz.PicService;
import com.ysmull.easemall.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author maoyusu
 */
@Controller
@RequestMapping("api")
public class PicController {

    private static final Long MAXPICSIZE = 3 * 1024 * 1000L;
    private final Logger log = LoggerFactory.getLogger(PicController.class);
    @Autowired
    PicService picService;


    @GetMapping("/pic/{id}")
    void getPic(@PathVariable("id") String uuid, HttpServletResponse res) throws Exception {
        picService.getPic(uuid, res);
    }

    @GetMapping("/pic/proxy")
    @Privilege(role = User.ROLE.SELLER)
    void picProxy(@RequestParam("picUrl") String picUrl, HttpServletResponse res) throws IOException {
        URL pic = new URL(picUrl);
        URLConnection conn = pic.openConnection();
        if (conn.getContentLengthLong() < MAXPICSIZE) {
            // 这里要设置content-length，否则递归调用该接口会无法正常返回
            res.setContentLengthLong(conn.getContentLengthLong());
            FileCopyUtils.copy(conn.getInputStream(), res.getOutputStream());
        }

    }
}
