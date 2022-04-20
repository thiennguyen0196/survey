package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.SurveyRelationshipModel

@Keep
data class SurveyRelationshipResponse(
    @SerializedName("questions")
    val questions: SurveyQuestionsResponse? = null
) {
    fun mapToModel(): SurveyRelationshipModel {
        return SurveyRelationshipModel(
            questions = this.questions?.mapToModel()
        )
    }
}