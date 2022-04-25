package com.thiennguyen.survey.data.interceptor

import com.thiennguyen.survey.data.Constants
import com.thiennguyen.survey.data.local.PreferenceManager
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor @Inject constructor(
    private val preferenceManager: PreferenceManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val tokenType = preferenceManager.getTokenType()
        val accessToken = preferenceManager.getAccessToken()
        return if (tokenType.isNullOrBlank() || accessToken.isNullOrBlank()) {
            chain.proceed(request)
        } else {
            val authenticationRequest = request.newBuilder()
                .header(Constants.Authorization.HEADER_AUTHORIZATION, "$tokenType $accessToken")
                .build()
            chain.proceed(authenticationRequest)
        }
    }
}
