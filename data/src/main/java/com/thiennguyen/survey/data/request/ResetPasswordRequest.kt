package com.thiennguyen.survey.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResetPasswordRequest(
    @SerializedName("user")
    val user: UserInfoRequest? = null,

    @SerializedName("client_id")
    val clientId: String? = null,

    @SerializedName("client_secret")
    val clientSecret: String? = null,
)