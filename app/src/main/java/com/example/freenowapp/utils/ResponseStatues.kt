package com.example.freenowapp.utils

data class ResponseStatues<out T>(val status: Status?, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ResponseStatues<T> {
            return ResponseStatues(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): ResponseStatues<T> {
            return ResponseStatues(Status.ERROR, null, msg)
        }

    }

}