package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String? = null,

    val type: String? = null,

    val attributes: UserAttributesModel? = null
) : Parcelable