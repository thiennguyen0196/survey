package com.thiennguyen.survey.data.response

import com.google.gson.annotations.SerializedName

class MetaResponse(
    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    val message: String? = null
)