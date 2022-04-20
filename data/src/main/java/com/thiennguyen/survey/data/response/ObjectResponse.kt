package com.thiennguyen.survey.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

class ObjectResponse<T> : BaseResponse() {

    @SerializedName("data")
    val data: T? = null
}
