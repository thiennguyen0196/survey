package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.BuildConfig
import com.thiennguyen.survey.data.annotation.GrantType
import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.request.LoginRequest
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticateService: SurveyService,
    private val preferenceManager: PreferenceManager
) : AuthenticationRepository {

    override fun submitLogin(email: String, password: String): Completable {
        return authenticateService.submitLogin(
            LoginRequest(
                grantType = GrantType.PASSWORD,
                email = email,
                password = password,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            )
        )
            .map { it.data!! }
            .doOnNext {
                preferenceManager.setUserLoggedInData(
                    accessToken = it.attributes?.accessToken,
                    refreshToken = it.attributes?.refreshToken
                )
            }
            .ignoreElements()
    }
}