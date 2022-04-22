package com.thiennguyen.survey.data.repository.base

import com.google.gson.Gson
import com.thiennguyen.survey.data.repository.testutils.TestUtils
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRepositoryTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var retrofit: Retrofit

    protected open val gson: Gson? = null

    @Before
    open fun setUp() {
        MockitoAnnotations.openMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder()
            .build()

        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson ?: Gson()))
            .client(okHttpClient)
            .build()
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    protected fun fakeResponseBodyFile(fileName: String, code: Int) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(TestUtils.getStringJson(fileName))
        )
    }
}
