package com.thiennguyen.survey.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.SurveyAttributesModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyAttributesResponse(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("thank_email_above_threshold")
    val thankEmailAboveThreshold: String? = null,

    @SerializedName("thank_email_below_threshold")
    val thankEmailBelowThreshold: String? = null,

    @SerializedName("is_active")
    val isActive: Boolean? = null,

    @SerializedName("cover_image_url")
    val coverImageUrl: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("active_at")
    val activeAt: String? = null,

    @SerializedName("inactive_at")
    val inactiveAt: String? = null,

    @SerializedName("survey_type")
    val surveyType: String? = null
) : Parcelable {

    fun mapToModel(): SurveyAttributesModel {
        return SurveyAttributesModel(
            title = this.title,
            description = this.description,
            thankEmailAboveThreshold = this.thankEmailAboveThreshold,
            thankEmailBelowThreshold = this.thankEmailBelowThreshold,
            isActive = this.isActive,
            coverImageUrl = this.coverImageUrl,
            createdAt = this.createdAt,
            inactiveAt = this.inactiveAt,
            surveyType = this.surveyType
        )
    }
}