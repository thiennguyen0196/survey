package com.thiennguyen.survey.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("meta")
    val meta: MetaResponse? = null
)
