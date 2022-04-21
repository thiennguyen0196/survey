package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.UserModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: SurveyRepository
) {

    fun getUserProfile(): Observable<UserModel> {
        return repository.getUserProfile()
    }
}