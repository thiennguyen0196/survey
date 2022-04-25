package com.thiennguyen.survey.data.annotation

import androidx.annotation.StringDef
import com.thiennguyen.survey.data.annotation.GrantType.Companion.PASSWORD
import com.thiennguyen.survey.data.annotation.GrantType.Companion.REFRESH_TOKEN

@StringDef(value = [PASSWORD, REFRESH_TOKEN])
@Retention(AnnotationRetention.SOURCE)
annotation class GrantType {
    companion object {
        const val PASSWORD = "password"
        const val REFRESH_TOKEN = "refresh_token"
    }
}
