package com.ysmull.easemall.controller.common;

import com.ysmull.easemall.exception.PicNotFoundException;
import com.ysmull.easemall.model.vo.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author maoyusu
 */
@ControllerAdvice
public class CustomerControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(CustomerControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResponse<String> handleBadRequestException(Exception ex, HttpServletResponse httpResponse) {
        log.error(ex.toString(), ex);
        WebResponse<String> response = new WebResponse<>();

        if (ex instanceof PicNotFoundException) {
            httpResponse.setStatus(WebResponse.NOT_FOUND);
            response.setCode(WebResponse.NOT_FOUND);
        } else {
            httpResponse.setStatus(WebResponse.SERVER_ERROR);
            response.setCode(WebResponse.SERVER_ERROR);
        }

        response.setMsg(ex.toString());
        return response;
    }

}