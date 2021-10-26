package com.example.freenowapp.utils

import com.example.freenowapp.errorHandling.AppException

data class ResponseStatus<out T>(val status: Status?, val data: T?, val appException: AppException?) {

    companion object {
        fun <T> success(data: T?): ResponseStatus<T> {
            return ResponseStatus(Status.SUCCESS, data, null)
        }

        fun <T> error(appException: AppException): ResponseStatus<T> {
            return ResponseStatus(Status.ERROR, null, appException)
        }
    }
}