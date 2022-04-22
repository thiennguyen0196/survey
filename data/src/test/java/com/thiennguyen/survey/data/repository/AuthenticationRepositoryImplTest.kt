package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.repository.base.BaseRepositoryTest
import com.thiennguyen.survey.data.service.SurveyService
import org.junit.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.anyString
import org.mockito.Mockito.spy
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.net.HttpURLConnection

class AuthenticationRepositoryImplTest : BaseRepositoryTest() {

    companion object {
        private const val SERVER_FAIL = "api-response/server_fail.json"
        private const val LOGIN_RESPONSE = "api-response/login_response.json"
    }

    private lateinit var repository: AuthenticationRepositoryImpl
    private val preferenceManager: PreferenceManager = mock()

    override fun setUp() {
        super.setUp()

        val service = retrofit.create(SurveyService::class.java)
        repository = spy(AuthenticationRepositoryImpl(surveyService = service, preferenceManager = preferenceManager))
    }

    @Test
    fun `When submit login success, then return complete and save user info`() {
        fakeResponseBodyFile(LOGIN_RESPONSE, HttpURLConnection.HTTP_OK)

        doNothing().`when`(preferenceManager).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
        repository.submitLogin("", "")
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(preferenceManager, times(1)).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When submit login fail, then return error and setTokenData method is not called`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        repository.submitLogin("", "")
            .test()
            .assertNotComplete()

        verify(preferenceManager, never()).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }
}