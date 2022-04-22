package com.thiennguyen.survey.data.response

import com.google.gson.annotations.SerializedName
import com.thiennguyen.survey.domain.model.MetaModel

class MetaResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("pages")
    val pages: Int? = null,

    @SerializedName("page_size")
    val pageSize: Int? = null,

    @SerializedName("records")
    val records: Int? = null
) {
    fun mapToModel(): MetaModel {
        return MetaModel(
            message = this.message,
            page = this.page,
            pages = this.pages,
            pageSize = this.pageSize,
            records = this.records
        )
    }
}