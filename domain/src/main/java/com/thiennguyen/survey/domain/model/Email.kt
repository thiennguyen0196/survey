package com.thiennguyen.survey.domain.model

import android.util.Patterns

@JvmInline
value class Email(val value: String) {
    fun isValid(): Boolean {
        return value.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
