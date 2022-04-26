package com.thiennguyen.survey.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.thiennguyen.survey.databinding.ViewTopSnackBarBinding

class TopSnackBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val binding: ViewTopSnackBarBinding = ViewTopSnackBarBinding.inflate(LayoutInflater.from(context), this, true)

    val tvTitle: AppCompatTextView
        get() = binding.tvTitle

    val tvMessage: AppCompatTextView
        get() = binding.tvMessage
}
