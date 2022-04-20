package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyRelationshipsModel(
    val questions: SurveyQuestionsModel? = null
): Parcelable