package com.thiennguyen.survey.domain.model

import androidx.core.util.PatternsCompat

@JvmInline
value class Email(val value: String) {
    fun isValid(): Boolean {
        return value.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(value).matches()
    }
}
