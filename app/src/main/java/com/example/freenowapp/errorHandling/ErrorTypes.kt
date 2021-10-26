package com.example.freenowapp.errorHandling

class NoConnectionException(code: Int) :
    AppException(errorCode = code)

class TimeOutException(code: Int) :
    AppException(errorCode = code)

class UnexpectedException(code: Int, throwable: Throwable) :
    AppException(errorCode = code, throwable = throwable)
