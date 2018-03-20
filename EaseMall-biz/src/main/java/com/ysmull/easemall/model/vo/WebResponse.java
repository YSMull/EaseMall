package com.ysmull.easemall.model.vo;

import java.io.Serializable;

/**
 * @author maoyusu
 */
public class WebResponse<T> implements Serializable {


    public static final int CLIENT_ERROR = 400;
    public static final int NOT_FOUND = 404;
    public static final int NO_AUTH = 401;
    public static final int SERVER_ERROR = 500;
    public static final int SUCCESS = 0;

    protected int code = SUCCESS;
    protected String msg;
    protected T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WebResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

