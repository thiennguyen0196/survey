package com.thiennguyen.survey.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.data.annotation.GrantType

@Keep
data class RefreshTokenRequest(
    @SerializedName("grant_type")
    @GrantType
    val grantType: String? = null,

    @SerializedName("refresh_token")
    val refreshToken: String? = null,

    @SerializedName("client_id")
    val clientId: String? = null,

    @SerializedName("client_secret")
    val clientSecret: String? = null
)