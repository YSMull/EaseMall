package com.ysmull.easemall.model.vo

import java.io.Serializable

/**
 * @author maoyusu
 */
class WebResponse<T> : Serializable {

    var code = SUCCESS
    var msg: String? = null
    var data: T? = null

    override fun toString(): String {
        return "WebResponse{" +
                "code=" + code +
                ", msg='" + msg + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {
        val CLIENT_ERROR = 400
        val NOT_FOUND = 404
        val NO_AUTH = 401
        val SERVER_ERROR = 500
        val SUCCESS = 0
    }
}

