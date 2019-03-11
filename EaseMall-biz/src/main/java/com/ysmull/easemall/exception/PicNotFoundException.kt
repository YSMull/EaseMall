package com.ysmull.easemall.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author maoyusu
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class PicNotFoundException : Exception {

    constructor(message: String) : super(message) {}


    constructor(message: String, cause: Exception) : super(message, cause) {}

}
