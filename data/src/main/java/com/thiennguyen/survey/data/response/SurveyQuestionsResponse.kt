package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.SurveyQuestionsModel

@Keep
data class SurveyQuestionsResponse(
    @SerializedName("data")
    val data: List<SurveyDataResponse>? = null
) {
    fun mapToModel(): SurveyQuestionsModel {
        return with(this) {
            SurveyQuestionsModel(
                data = data?.map { dataItemResponse ->
                    dataItemResponse.mapToModel()
                }
            )
        }
    }
}
