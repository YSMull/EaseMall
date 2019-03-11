package com.ysmull.easemall.controller.common

import com.ysmull.easemall.exception.PicNotFoundException
import com.ysmull.easemall.exception.RecordNotFoundException
import com.ysmull.easemall.model.vo.WebResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletResponse

/**
 * @author maoyusu
 */
@ControllerAdvice
class CustomerControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleBadRequestException(ex: Exception, httpResponse: HttpServletResponse): WebResponse<String> {
        log.error(ex.toString(), ex)
        val response = WebResponse<String>()

        if (ex is PicNotFoundException || ex is RecordNotFoundException) {
            httpResponse.status = WebResponse.NOT_FOUND
            response.code = WebResponse.NOT_FOUND
        } else {
            httpResponse.status = WebResponse.SERVER_ERROR
            response.code = WebResponse.SERVER_ERROR
        }

        response.msg = ex.toString()
        return response
    }

    companion object {

        private val log = LoggerFactory.getLogger(CustomerControllerAdvice::class.java)
    }

}
