package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CheckIsLoggedInUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun checkIsLoggedIn(): Observable<Boolean> {
        return repository.isLoggedIn()
    }
}