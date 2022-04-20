package com.thiennguyen.survey.data.network

import android.content.Context
import okhttp3.*
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class ServiceBuilder {

    // retrofit config
    private var callAdapterFactories: MutableList<CallAdapter.Factory> = arrayListOf()
    private var converterFactories: MutableList<Converter.Factory> = arrayListOf()
    private val headers: MutableList<Header> = ArrayList()
    private var authenticator: Authenticator? = null

    private val interceptors: MutableList<Interceptor> = ArrayList()
    private var followRedirect: Boolean = true
    private var context: Context? = null

    private var customClientBuilder: OkHttpClient.Builder? = null
    private var connectTimeout: Pair<Long, TimeUnit>? = null
    private var readTimeout: Pair<Long, TimeUnit>? = null
    private var writeTimeout: Pair<Long, TimeUnit>? = null

    fun addCallAdapterFactories(callAdapterFactory: CallAdapter.Factory): ServiceBuilder {
        this.callAdapterFactories.add(callAdapterFactory)
        return this
    }

    fun addConverterFactories(converterFactory: Converter.Factory): ServiceBuilder {
        this.converterFactories.add(converterFactory)
        return this
    }

    fun addHeader(header: Header): ServiceBuilder {
        headers.add(header)
        return this
    }

    fun setAuthenticator(authenticator: Authenticator): ServiceBuilder {
        this.authenticator = authenticator
        return this
    }

    fun addInterceptor(interceptor: Interceptor): ServiceBuilder {
        interceptors.add(interceptor)
        return this
    }

    fun configOkHttpClient(
        connectTimeout: Pair<Long, TimeUnit>? = null,
        readTimeout: Pair<Long, TimeUnit>? = null,
        writeTimeout: Pair<Long, TimeUnit>? = null
    ): ServiceBuilder {
        this.connectTimeout = connectTimeout
        this.readTimeout = readTimeout
        this.writeTimeout = writeTimeout
        return this
    }

    fun addContextMTLS(context: Context): ServiceBuilder {
        this.context = context
        return this
    }

    fun configOkHttpClient(client: OkHttpClient.Builder? = null): ServiceBuilder {
        this.customClientBuilder = client
        return this
    }

    /**
     * Build service with given configs
     * @param clazz Service Class need to be created
     * @param endpoint Service Endpoint for Retrofit
     */
    fun <T> build(clazz: Class<T>, endpoint: String): T {
        /**
         * Init Config
         */
        val clientBuilder = customClientBuilder ?: OkHttpClient.Builder().apply {
            connectTimeout?.let { connectTimeout(it.first, it.second) }
            readTimeout?.let { readTimeout(it.first, it.second) }
            writeTimeout?.let { writeTimeout(it.first, it.second) }
        }

        /**
         * Header Config with an interceptor
         */
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()

            for (header: Header in headers) {
                header.provideValue()?.let { builder.addHeader(header.key, it) }
            }
            builder.method(original.method, original.body)
            chain.proceed(builder.build())
        }

        /**
         * Authenticator Config
         */
        authenticator?.let { clientBuilder.authenticator(it) }

        /**
         * Other Settings
         */
        clientBuilder.followRedirects(followRedirect)

        /**
         * Interceptor Config
         */
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        /**
         * Retrofit Config
         */
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(clientBuilder.build())

        for (callAdapterFactory in callAdapterFactories) {
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory)
        }

        for (converterFactory in converterFactories) {
            retrofitBuilder.addConverterFactory(converterFactory)
        }

        /**
         * Wrap Retrofit with CloudFlare Config
         */
        return retrofitBuilder.build().create(clazz)
    }

    abstract class Header constructor(val key: String) {
        abstract fun provideValue(): String?
    }

    abstract class AccessTokenHeader : Header("Authorization")
}
