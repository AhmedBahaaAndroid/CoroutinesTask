package com.example.freenowapp.utils

import com.example.freenowapp.NO_CONNECTION
import com.example.freenowapp.TIME_OUT
import com.example.freenowapp.UNEXPECTED
import com.example.freenowapp.errorHandling.AppException
import com.example.freenowapp.errorHandling.NoConnectionException
import com.example.freenowapp.errorHandling.TimeOutException
import com.example.freenowapp.errorHandling.UnexpectedException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun mapThrowable(throwable: Throwable): AppException {
    return when (throwable) {
        is SocketTimeoutException,
        is TimeOutException -> TimeOutException(
            code = TIME_OUT
        )
        is UnknownHostException -> NoConnectionException(
            code = NO_CONNECTION
        )
        else -> {
            UnexpectedException(
                code = UNEXPECTED,
                throwable = throwable
            )
        }
    }
}

