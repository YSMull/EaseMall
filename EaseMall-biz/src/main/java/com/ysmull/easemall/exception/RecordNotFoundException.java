package com.ysmull.easemall.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author maoyusu
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception {

    public RecordNotFoundException(String message) {
        super(message);
    }


    public RecordNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
