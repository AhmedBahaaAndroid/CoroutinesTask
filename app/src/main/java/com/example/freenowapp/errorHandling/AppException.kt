package com.example.freenowapp.errorHandling

open class AppException(
    val errorCode: Int,
    val throwable: Throwable? = null
) : Throwable()
