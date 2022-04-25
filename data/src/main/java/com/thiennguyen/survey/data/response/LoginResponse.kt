package com.thiennguyen.survey.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("attributes")
    val attributes: LoginAttributesResponse? = null
)
