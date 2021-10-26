package com.example.freenowapp.extensions

import androidx.annotation.StringRes
import com.example.freenowapp.NO_CONNECTION
import com.example.freenowapp.R
import com.example.freenowapp.TIME_OUT
import com.example.freenowapp.UNEXPECTED
import com.example.freenowapp.errorHandling.AppException

@StringRes
fun AppException.handleError(): Int {
    return when (errorCode) {
        TIME_OUT -> R.string.timeout_error_msg
        NO_CONNECTION -> R.string.no_internet_connection_error_msg
        UNEXPECTED -> R.string.un_expected_error_msg
        else -> R.string.un_expected_error_msg
    }
}