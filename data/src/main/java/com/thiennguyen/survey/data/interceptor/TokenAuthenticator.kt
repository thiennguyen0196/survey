package com.thiennguyen.survey.data.interceptor

import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import dagger.Lazy
import java.lang.Exception
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class TokenAuthenticator(
    private val repository: Lazy<AuthenticationRepository>,
    private val preferenceManager: PreferenceManager,
    private val logoutService: LogoutService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = preferenceManager.getAccessToken() ?: return null
        val tokenType = preferenceManager.getTokenType() ?: return null

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                val newAccessToken = preferenceManager.getAccessToken().orEmpty()
                if (response.request.header(Constants.Authorization.HEADER_AUTHORIZATION) != null) {
                    if (newAccessToken != accessToken) {
                        return appendToken(response, tokenType, newAccessToken)
                    }
                    if (retryCount(response) < Constants.Retry.LIMIT) {
                        try {
                            repository.get().refreshToken(preferenceManager.getRefreshToken().orEmpty())
                                .timeout(15, TimeUnit.SECONDS)
                                .blockingSubscribe()
                            return appendToken(response, tokenType, preferenceManager.getAccessToken().orEmpty())
                        } catch (e: Exception) {
                            Timber.i("Refreshing token failed, retry again >>> $e")
                        }
                    } else {
                        Timber.i("Retrying to refresh token is out of limit, start logout service")
                        preferenceManager.clear()
                        logoutService.startService()
                    }
                }
            }
        }
        return null
    }

    private fun appendToken(response: Response, tokenType: String, accessToken: String): Request {
        return response.request
            .newBuilder()
            .header(Constants.Authorization.HEADER_AUTHORIZATION, "$tokenType $accessToken")
            .build()
    }

    private fun retryCount(response: Response?): Int {
        var priorResponse = response
        var result = 1
        while (priorResponse?.priorResponse != null) {
            priorResponse = priorResponse.priorResponse
            result++
        }
        return result
    }
}
