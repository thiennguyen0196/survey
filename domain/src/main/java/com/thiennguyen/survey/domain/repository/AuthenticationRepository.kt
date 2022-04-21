package com.thiennguyen.survey.domain.repository

import com.thiennguyen.survey.domain.model.MetaModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AuthenticationRepository {

    fun submitLogin(email: String, password: String): Completable

    fun refreshToken(refreshToken: String): Completable

    fun isLoggedIn(): Observable<Boolean>

    fun resetPassword(email: String): Observable<MetaModel>
}