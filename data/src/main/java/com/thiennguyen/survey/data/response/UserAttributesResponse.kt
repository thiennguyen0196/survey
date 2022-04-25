package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.UserAttributesModel

@Keep
data class UserAttributesResponse(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null
) {
    fun mapToModel(): UserAttributesModel {
        return with(this) {
            UserAttributesModel(
                email = email,
                avatarUrl = avatarUrl
            )
        }
    }
}
