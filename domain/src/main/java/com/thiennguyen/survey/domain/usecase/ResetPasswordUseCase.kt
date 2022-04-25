package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.Email
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.repository.AuthenticationRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun resetPassword(email: Email): Observable<MetaModel> {
        return repository.resetPassword(email)
    }
}
