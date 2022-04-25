package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.Password
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun submitLogin(email: Email, password: Password): Completable {
        return repository.submitLogin(email = email, password = password)
    }
}
