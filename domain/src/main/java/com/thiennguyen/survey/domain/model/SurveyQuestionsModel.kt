package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyQuestionsModel(
    val data: List<SurveyDataModel>? = null
) : Parcelable