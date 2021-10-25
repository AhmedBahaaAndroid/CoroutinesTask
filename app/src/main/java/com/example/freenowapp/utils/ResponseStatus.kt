package com.example.freenowapp.utils

data class ResponseStatus<out T>(val status: Status?, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): ResponseStatus<T> {
            return ResponseStatus(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): ResponseStatus<T> {
            return ResponseStatus(Status.ERROR, null, msg)
        }
    }
}