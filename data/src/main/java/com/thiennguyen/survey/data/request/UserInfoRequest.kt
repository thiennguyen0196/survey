package com.thiennguyen.survey.data.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserInfoRequest(
    @SerializedName("email")
    val email: String? = null
)
