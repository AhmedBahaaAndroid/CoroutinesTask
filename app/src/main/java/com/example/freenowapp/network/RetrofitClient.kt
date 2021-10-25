package com.example.freenowapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val converterFactory = createGsonConverter(builder.jsonConverterAdaptersList)
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
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
        internal var jsonConverterAdaptersList: List<Pair<Type, Any>>? = emptyList()

        fun build(buildBlock: Builder.() -> Unit = {}): RetrofitClient {
            buildBlock()
            return RetrofitClient(this)
        }

        fun useDefaultLoggerInterceptor() = apply {
            this.useLoggerInterceptor = true
        }

        fun setCustomConverterAdapter(customJsonConverters: ICustomJsonConverters?) {
            this.jsonConverterAdaptersList = customJsonConverters?.getConverters()
        }
    }

    interface ICustomJsonConverters {
        fun getConverters(): List<Pair<Type, Any>>?
    }

    companion object {
        private const val DEFAULT_TIME_OUT_SECONDS = 25L
    }
}
