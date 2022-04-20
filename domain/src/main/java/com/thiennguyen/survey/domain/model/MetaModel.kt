package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaModel(
    val page: Int? = null,

    val pages: Int? = null,

    val pageSize: Int? = null,

    val records: Int? = null
) : Parcelable