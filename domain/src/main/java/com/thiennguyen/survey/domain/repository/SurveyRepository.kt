package com.thiennguyen.survey.domain.repository

import com.thiennguyen.survey.domain.model.MetaModel
import com.thiennguyen.survey.domain.model.SurveyModel
import com.thiennguyen.survey.domain.model.UserModel
import io.reactivex.rxjava3.core.Observable

interface SurveyRepository {

    fun getSurveyList(page: Int, pageSize: Int): Observable<Pair<MetaModel, List<SurveyModel>>>

    fun getUserProfile(): Observable<UserModel>
}
