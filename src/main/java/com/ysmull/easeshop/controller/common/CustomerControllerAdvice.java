package com.ysmull.easeshop.controller.common;

import com.ysmull.easeshop.model.vo.WebResponse;
import com.ysmull.easeshop.util.PrivilegeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyusu
 */
@ControllerAdvice
public class CustomerControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(CustomerControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WebResponse<String> handleBadRequestException(Exception ex) {
        log.error(ex.toString(), ex);
        WebResponse<String> response = new WebResponse<>();
        response.setData(ex.toString());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMsg("error");
        return response;
    }

}