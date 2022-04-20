package com.thiennguyen.survey.data.response

import com.google.gson.annotations.SerializedName

class ObjectList<T> : BaseResponse() {

    @SerializedName("data")
    val data: List<T>? = null
}
