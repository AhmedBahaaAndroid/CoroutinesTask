package com.example.freenowapp.errorHandling

class NoConnectionException(code: Int) : AppException(errorCode = code)

class TimeOutException(code: Int) : AppException(errorCode = code)

class UnexpectedException(code: Int) : AppException(errorCode = code)
