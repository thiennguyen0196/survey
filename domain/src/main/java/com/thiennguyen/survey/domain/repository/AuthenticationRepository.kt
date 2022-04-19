package com.thiennguyen.survey.domain.repository

import io.reactivex.rxjava3.core.Completable

interface AuthenticationRepository {

    fun submitLogin(email: String, password: String): Completable
}