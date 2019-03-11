package com.ysmull.easemall.controller.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/")
class MainPageController {


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/")
    fun mainPage(): ModelAndView {
        return ModelAndView("forward://index.html")
    }
}
