package com.thiennguyen.survey.domain.repository

import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.Password
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AuthenticationRepository {

    fun submitLogin(email: Email, password: Password): Completable

    fun refreshToken(refreshToken: String): Completable

    fun isLoggedIn(): Observable<Boolean>

    fun resetPassword(email: Email): Observable<MetaModel>
}
