package com.thiennguyen.survey.domain.usecase

import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetSurveyListUseCase @Inject constructor(
    private val repository: SurveyRepository
) {

    fun getSurveyList(page: Int, pageSize: Int): Observable<Pair<MetaModel, List<SurveyModel>>> {
        return repository.getSurveyList(page, pageSize)
    }
}
