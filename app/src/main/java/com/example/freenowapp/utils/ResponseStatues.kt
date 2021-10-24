package com.example.freenowapp.utils

data class ResponseStatues<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ResponseStatues<T> {
            return ResponseStatues(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResponseStatues<T> {
            return ResponseStatues(Status.ERROR, data, msg)
        }

        fun <T> loading(): ResponseStatues<T> {
            return ResponseStatues(Status.LOADING, null, null)
        }

    }

}