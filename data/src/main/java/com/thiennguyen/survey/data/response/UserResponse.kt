package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.UserModel

@Keep
data class UserResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("attributes")
    val attributes: UserAttributesResponse? = null
) {
    fun mapToModel(): UserModel {
        return with(this) {
            UserModel(
                id = id,
                type = type,
                attributes = attributes?.mapToModel()
            )
        }
    }
}
