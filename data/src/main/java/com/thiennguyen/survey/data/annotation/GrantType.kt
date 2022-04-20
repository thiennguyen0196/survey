package com.thiennguyen.survey.data.annotation

import androidx.annotation.StringDef
import com.thiennguyen.survey.data.annotation.GrantType.Companion.PASSWORD

@StringDef(value = [PASSWORD])
@Retention(AnnotationRetention.SOURCE)
annotation class GrantType {
    companion object {
        const val PASSWORD = "password"
    }
}