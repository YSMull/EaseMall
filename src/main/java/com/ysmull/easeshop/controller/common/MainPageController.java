package com.ysmull.easeshop.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author maoyusu
 */
@RestController
@RequestMapping("/")
public class MainPageController {


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("forward://index.html");
    }
}