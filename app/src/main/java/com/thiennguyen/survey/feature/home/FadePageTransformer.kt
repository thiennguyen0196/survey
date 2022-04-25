package com.thiennguyen.survey.feature.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class FadePageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        if (position <= -1.0f || position >= 1.0f) {
            view.translationX = view.width * position
            view.alpha = 0.0f
        } else if (position == 0.0f) {
            view.translationX = view.width * position
            view.alpha = 1.0f
        } else {
            view.translationX = view.width * -position
            view.alpha = 1.0f - abs(position)
        }
    }
}
