package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.BuildConfig
import com.thiennguyen.survey.data.annotation.GrantType
import com.thiennguyen.survey.data.local.PreferenceManager
import com.thiennguyen.survey.data.request.ResetPasswordRequest
import com.thiennguyen.survey.data.request.LoginRequest
import com.thiennguyen.survey.data.request.RefreshTokenRequest
import com.thiennguyen.survey.data.request.UserInfoRequest
import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val preferenceManager: PreferenceManager
) : AuthenticationRepository {

    override fun submitLogin(email: String, password: String): Completable {
        return surveyService.submitLogin(
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
                preferenceManager.setTokenData(
                    accessToken = it.attributes?.accessToken,
                    refreshToken = it.attributes?.refreshToken,
                    tokenType = it.attributes?.tokenType,
                    createdAt = it.attributes?.createdAt,
                    expiredIn = it.attributes?.expiresIn
                )
            }
            .ignoreElements()
    }

    override fun refreshToken(refreshToken: String): Completable {
        return surveyService.refreshToken(
            RefreshTokenRequest(
                grantType = GrantType.REFRESH_TOKEN,
                refreshToken = refreshToken,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            )
        )
            .map { it.data!! }
            .doOnNext {
                preferenceManager.setTokenData(
                    accessToken = it.attributes?.accessToken,
                    refreshToken = it.attributes?.refreshToken,
                    tokenType = it.attributes?.tokenType,
                    createdAt = it.attributes?.createdAt,
                    expiredIn = it.attributes?.expiresIn
                )
            }
            .ignoreElements()
    }

    override fun isLoggedIn(): Observable<Boolean> {
        return Observable.defer {
            Observable.just(preferenceManager.getIsLoggedIn())
        }
    }

    override fun resetPassword(email: String): Observable<MetaModel> {
        return surveyService.resetPassword(
            ResetPasswordRequest(
                user = UserInfoRequest(email = email),
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            )
        )
            .map { it.meta?.mapToModel()!! }
    }
}