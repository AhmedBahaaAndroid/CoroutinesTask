package com.example.freenowapp.network

import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class RetrofitClient (builder: Builder) {

    private val retrofit: Retrofit

    init {
        val baseUrl = builder.baseUrl
        val readTimeOut = builder.readTimeOutSeconds ?: DEFAULT_TIME_OUT_SECONDS
        val writeTimeout = builder.writeTimeoutSeconds ?: DEFAULT_TIME_OUT_SECONDS
        val connectTimeout = builder.connectTimeoutSeconds ?: DEFAULT_TIME_OUT_SECONDS
        val useLoggerInterceptor = builder.useLoggerInterceptor
        val interceptors = builder.interceptors
        val callAdapters = builder.callAdapters
        val authenticator = builder.authenticator
        val cache = builder.cache
        val converterFactory = createGsonConverter(builder.jsonConverterAdaptersList)

        val okHttpClient = OkHttpClient().newBuilder()
            .useDefaultLoggerInterceptor(useLoggerInterceptor)
            .addInterceptors(interceptors)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .cache(cache)
            .authenticator(authenticator)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapters(callAdapters)
            .build()
    }

    private fun createGsonConverter(jsonConvertAdaptersList: List<Pair<Type, Any>>?): Converter.Factory {
        val gsonBuilder = GsonBuilder()
        jsonConvertAdaptersList?.forEach {
            gsonBuilder.registerTypeAdapter(it.first, it.second)
        }

        return GsonConverterFactory.create(gsonBuilder.create())
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    class Builder(internal val baseUrl: String) {

        internal var connectTimeoutSeconds: Long? = null
        internal var readTimeOutSeconds: Long? = null
        internal var writeTimeoutSeconds: Long? = null
        internal var useLoggerInterceptor: Boolean = false
        internal var callAdapters: MutableList<CallAdapter.Factory> = mutableListOf()
        internal var interceptors: MutableList<Interceptor> = mutableListOf()
        internal var authenticator: Authenticator = Authenticator.NONE
        internal var cache: Cache? = null
        internal var jsonConverterAdaptersList: List<Pair<Type, Any>>? = emptyList()

        fun build(buildBlock: Builder.() -> Unit = {}): RetrofitClient {
            buildBlock()
            return RetrofitClient(this)
        }

        fun useDefaultLoggerInterceptor() = apply {
            this.useLoggerInterceptor = true
        }

        fun addCallAdapter(callAdapterFactory: CallAdapter.Factory) = apply {
            this.callAdapters.add(callAdapterFactory)
        }

        fun addInterceptor(interceptor: Interceptor) = apply {
            this.interceptors.add(interceptor)
        }

        fun setAuthenticator(authenticator: Authenticator) = apply {
            this.authenticator = authenticator
        }

        fun setConnectTimeoutSeconds(connectTimeoutSeconds: Long) = apply {
            this.connectTimeoutSeconds = connectTimeoutSeconds
        }

        fun setReadTimeOutSeconds(readTimeOutSeconds: Long) = apply {
            this.readTimeOutSeconds = readTimeOutSeconds
        }

        fun setWriteTimeoutSeconds(writeTimeoutSeconds: Long) = apply {
            this.writeTimeoutSeconds = writeTimeoutSeconds
        }

        fun setCache(cache: Cache) = apply {
            this.cache = cache
        }

        fun setCustomConverterAdapter(customJsonConverters: ICustomJsonConverters?) {
            this.jsonConverterAdaptersList = customJsonConverters?.getConverters()
        }
    }

    interface ICustomJsonConverters {
        fun getConverters(): List<Pair<Type, Any>>?
    }

    companion object {
        private val DEFAULT_TIME_OUT_SECONDS = 25L
    }
}
