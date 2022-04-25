package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.SurveyDataModel

@Keep
data class SurveyDataResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val type: String? = null
) {
    fun mapToModel(): SurveyDataModel {
        return with(this) {
            SurveyDataModel(
                id = id,
                type = type
            )
        }
    }
}
