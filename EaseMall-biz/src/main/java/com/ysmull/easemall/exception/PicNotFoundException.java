package com.ysmull.easemall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author maoyusu
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PicNotFoundException extends Exception {

    public PicNotFoundException(String message) {
        super(message);
    }


    public PicNotFoundException(String message, Exception cause) {
        super(message, cause);
    }

}
