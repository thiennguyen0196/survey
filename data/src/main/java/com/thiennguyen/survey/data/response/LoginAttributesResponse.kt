package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginAttributesResponse(
    @SerializedName("access_token")
    val accessToken: String? = null,

    @SerializedName("created_at")
    val createdAt: Int? = null,

    @SerializedName("expires_in")
    val expiresIn: Int? = null,

    @SerializedName("refresh_token")
    val refreshToken: String? = null,

    @SerializedName("token_type")
    val tokenType: String? = null
)