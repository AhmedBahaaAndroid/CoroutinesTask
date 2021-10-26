package com.example.freenowapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class RetrofitClient (builder: Builder) {

    private val retrofit: Retrofit

    init {
        val baseUrl = builder.baseUrl
        val converterFactory = createGsonConverter(builder.jsonConverterAdaptersList)
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logger)
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

        private var useLoggerInterceptor: Boolean = false
        var jsonConverterAdaptersList: List<Pair<Type, Any>>? = emptyList()

        fun build(buildBlock: Builder.() -> Unit = {}): RetrofitClient {
            buildBlock()
            return RetrofitClient(this)
        }

        fun useDefaultLoggerInterceptor() = apply {
            this.useLoggerInterceptor = true
        }
    }
}
