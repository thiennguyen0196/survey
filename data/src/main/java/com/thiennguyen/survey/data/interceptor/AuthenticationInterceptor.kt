package com.thiennguyen.survey.data.interceptor

import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.utils.isValidAccessToken
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import dagger.Lazy
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val authenticationRepository: Lazy<AuthenticationRepository>,
    private val preferenceManager: PreferenceManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val tokenType = preferenceManager.getTokenType().orEmpty()
        return if (checkAccessToken(request)) {
            val response = chain.proceed(appendTokenType(request, tokenType))
            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshTokenAndUpdateRequest(request).let {
                    chain.proceed(appendTokenType(it, tokenType))
                }
            } else {
                response
            }
        } else {
            refreshTokenAndUpdateRequest(request).let {
                chain.proceed(appendTokenType(it, tokenType))
            }
        }
    }

    /**
     * Try to get new refresh access token. If failed, clear local data and open login
     */
    private fun refreshTokenAndUpdateRequest(request: Request): Request {
        synchronized(this) {
            // If another request already executed this block and refresh token successfully.
            // Return request with new access token
            if (hasNewAccessToken(request)) {
                return updateRequestWithNewAccessToken(request)
            }

            val refreshToken = preferenceManager.getRefreshToken()
            try {
                authenticationRepository.get().refreshToken(refreshToken.orEmpty())
                    .blockingSubscribe()
                return updateRequestWithNewAccessToken(request)
            } catch (e: Exception) {
                Timber.e(e, "REFRESH TOKEN FAILED")
                preferenceManager.clear()
                return request
            }
        }
    }

    /**
     * Compare access token in current request with access token in preferences
     * @return true if access token of request != access token in preferences
     * and access token in preferences is valid
     */
    private fun hasNewAccessToken(request: Request): Boolean {
        val accessToken = preferenceManager.getAccessToken()
        val expiredIn = preferenceManager.getExpiredIn()
        return request.header(Constants.Authorization.HEADER_AUTHORIZATION) != accessToken &&
                accessToken.isValidAccessToken(expiredIn)
    }

    /**
     * Check access token validation in request header
     * If it doesn't exist, return true
     * If it exists, return true if is valid, false otherwise
     */
    private fun checkAccessToken(request: Request): Boolean {
        val accessToken = request.header(Constants.Authorization.HEADER_AUTHORIZATION)
        val expiredIn = preferenceManager.getExpiredIn()
        return accessToken?.isValidAccessToken(expiredIn) ?: true
    }

    /**
     * Update new access token for request
     */
    private fun updateRequestWithNewAccessToken(request: Request): Request {
        return request.newBuilder()
            .removeHeader(Constants.Authorization.HEADER_AUTHORIZATION)
            .addHeader(Constants.Authorization.HEADER_AUTHORIZATION, preferenceManager.getAccessToken().orEmpty())
            .build()
    }

    /**
     * Append token type to access token only if access token exists in header
     */
    private fun appendTokenType(request: Request, tokenType: String): Request {
        var accessToken = request.header(Constants.Authorization.HEADER_AUTHORIZATION)
        return if (accessToken == null || accessToken.startsWith(tokenType))
            request
        else {
            accessToken = "$tokenType $accessToken"
            request.newBuilder()
                .removeHeader(Constants.Authorization.HEADER_AUTHORIZATION)
                .addHeader(Constants.Authorization.HEADER_AUTHORIZATION, accessToken)
                .build()
        }
    }
}
