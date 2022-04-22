package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.base.BaseRepositoryTest
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.model.MetaModel
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.anyString
import org.mockito.Mockito.spy
import org.mockito.kotlin.*
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class AuthenticationRepositoryImplTest : BaseRepositoryTest() {

    companion object {
        private const val SERVER_FAIL = "api-response/server_fail.json"
        private const val LOGIN_RESPONSE = "api-response/login_response.json"
        private const val REFRESH_RESPONSE = "api-response/refresh_response.json"
        private const val DATA_NULL_RESPONSE = "api-response/data_null_response.json"
        private const val FORGOT_PASSWORD_RESPONSE = "api-response/forgot_password_response.json"
    }

    private lateinit var repository: AuthenticationRepositoryImpl
    private val preferenceManager: PreferenceManager = mock()

    override fun setUp() {
        super.setUp()

        val service = retrofit.create(SurveyService::class.java)
        repository = spy(AuthenticationRepositoryImpl(surveyService = service, preferenceManager = preferenceManager))
    }

    @Test
    fun `When submit login returns success, then return complete and save user info`() {
        fakeResponseBodyFile(LOGIN_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Unit>()
        doNothing().`when`(preferenceManager).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
        repository.submitLogin("", "").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertNoErrors()

        verify(preferenceManager, times(1)).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When submit login returns success, but data is null, then return error and not save user info`() {
        fakeResponseBodyFile(DATA_NULL_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Unit>()
        repository.submitLogin("", "").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }

        verify(preferenceManager, never()).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When submit login fail, then return error and setTokenData method is not called`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = TestObserver<Unit>()
        repository.submitLogin("", "").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is HttpException }

        verify(preferenceManager, never()).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When refresh token success, then return complete and save user info`() {
        fakeResponseBodyFile(REFRESH_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Unit>()
        doNothing().`when`(preferenceManager).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
        repository.refreshToken("").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertNoErrors()

        verify(preferenceManager, times(1)).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When refresh token returns success, but data is null, then return error and not save user info`() {
        fakeResponseBodyFile(DATA_NULL_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<Unit>()
        repository.refreshToken("").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is NullPointerException }

        verify(preferenceManager, never()).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When refresh token fail, then return error and setTokenData method is not called`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = TestObserver<Unit>()
        repository.refreshToken("").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is HttpException }

        verify(preferenceManager, never()).setTokenData(anyString(), anyString(), anyString(), anyLong(), anyLong())
    }

    @Test
    fun `When preference manager returns true for checking login, then return true`() {
        val testObserver = TestObserver<Boolean>()
        whenever(preferenceManager.getIsLoggedIn()).thenReturn(true)
        repository.isLoggedIn().subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertValue(true)
    }

    @Test
    fun `When preference manager returns false for checking login, then return false`() {
        val testObserver = TestObserver<Boolean>()
        whenever(preferenceManager.getIsLoggedIn()).thenReturn(false)
        repository.isLoggedIn().subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()

        testObserver.assertValue(false)
    }

    @Test
    fun `When reset password success, then return meta data`() {
        fakeResponseBodyFile(FORGOT_PASSWORD_RESPONSE, HttpURLConnection.HTTP_OK)

        val testObserver = TestObserver<MetaModel>()
        repository.resetPassword("").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { it.message == "If your email address exists in our database, you will receive a password recovery link at your email address in a few minutes." }
    }

    @Test
    fun `When reset password fail, then return error`() {
        fakeResponseBodyFile(SERVER_FAIL, HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = TestObserver<MetaModel>()
        repository.resetPassword("").subscribe(testObserver)

        testObserver.awaitDone(10, TimeUnit.SECONDS)
        testObserver.assertNotComplete()
        testObserver.assertError { it is HttpException }
    }
}