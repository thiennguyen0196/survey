package com.thiennguyen.survey.data.repository

import com.thiennguyen.survey.data.service.SurveyService
import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.repository.SurveyRepository
import io.reactivex.rxjava3.core.Observable
import java.lang.NullPointerException
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyService: SurveyService
) : SurveyRepository {

    override fun getSurveyList(page: Int, pageSize: Int): Observable<Pair<MetaModel, List<SurveyModel>>> {
        return surveyService.getSurveyList(pageNumber = page, pageSize = pageSize)
            .map {
                val surveyList = it.data?.map { item ->
                    item.mapToModel()
                }
                val metaModel = it.meta?.mapToModel()
                if (surveyList == null || metaModel == null) {
                    throw NullPointerException("Survey list or Meta data is null")
                } else {
                    Pair(metaModel, surveyList)
                }
            }
    }
}