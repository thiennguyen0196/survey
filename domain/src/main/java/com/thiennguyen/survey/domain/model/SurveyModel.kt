package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyModel(
    val id: String? = null,

    val type: String? = null,

    val attributes: SurveyAttributesModel? = null,

    val relationships: SurveyRelationshipsModel? = null
) : Parcelable