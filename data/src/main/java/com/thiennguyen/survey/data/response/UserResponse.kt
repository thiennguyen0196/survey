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
        return UserModel(
            id = this.id,
            type = this.type,
            attributes = this.attributes?.mapToModel()
        )
    }
}