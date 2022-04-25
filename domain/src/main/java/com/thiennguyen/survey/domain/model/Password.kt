package com.thiennguyen.survey.domain.model

@JvmInline
value class Password(val value: String) {
    fun isValid(): Boolean {
        return value.isNotBlank()
    }
}
