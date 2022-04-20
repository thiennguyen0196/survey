package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyAttributesModel(
    val title: String? = null,

    val description: String? = null,

    val thankEmailAboveThreshold: String? = null,

    val thankEmailBelowThreshold: String? = null,

    val isActive: Boolean? = null,

    val coverImageUrl: String? = null,

    val createdAt: String? = null,

    val activeAt: String? = null,

    val surveyType: String?
) : Parcelable