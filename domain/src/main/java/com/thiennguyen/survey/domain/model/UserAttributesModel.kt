package com.thiennguyen.survey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAttributesModel(
    val email: String? = null,

    val avatarUrl: String? = null
): Parcelable