package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun submitLogin(email: String, password: String): Completable {
        return repository.submitLogin(email = email, password = password)
    }
}