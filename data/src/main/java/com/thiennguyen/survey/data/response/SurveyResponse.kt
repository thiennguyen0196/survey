package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.SurveyModel

@Keep
data class SurveyResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("attributes")
    val attributes: SurveyAttributesResponse? = null,

    @SerializedName("relationships")
    val relationships: SurveyRelationshipResponse? = null
) {
    fun mapToModel(): SurveyModel {
        return with(this) {
            SurveyModel(
                id = id,
                type = type,
                attributes = attributes?.mapToModel(),
                relationships = relationships?.mapToModel()
            )
        }
    }
}
