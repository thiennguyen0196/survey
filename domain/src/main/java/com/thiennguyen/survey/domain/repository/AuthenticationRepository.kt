package com.thiennguyen.survey.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AuthenticationRepository {

    fun submitLogin(email: String, password: String): Completable

    fun refreshToken(refreshToken: String): Completable

    fun isLoggedIn(): Observable<Boolean>
}