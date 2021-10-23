package com.example.freenowapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit

fun Retrofit.Builder.addCallAdapters(callAdapters: List<CallAdapter.Factory>) = apply {
    callAdapters.forEach { callAdapter ->
        addCallAdapterFactory(callAdapter)
    }
}

fun OkHttpClient.Builder.addInterceptors(interceptors: List<Interceptor>) = apply {
    interceptors.forEach { interceptor ->
        addInterceptor(interceptor)
    }
}

fun OkHttpClient.Builder.useDefaultLoggerInterceptor(useLoggerInterceptor: Boolean) = apply {
    if (useLoggerInterceptor) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(logger)
    }
}
