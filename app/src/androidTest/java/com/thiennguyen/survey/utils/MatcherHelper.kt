package com.thiennguyen.survey.utils

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withDrawable(
    @DrawableRes drawableId: Int,
    @ColorRes colorRes: Int? = null,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText(
                "ImageView with drawable same as drawable with id: $drawableId >>> tint color id: $colorRes >>> mode: $tintMode"
            )
        }

        override fun matchesSafely(view: View): Boolean {
            val colorInt = colorRes?.let {
                ContextCompat.getColor(view.context, it)
            }
            val expectedBitmap = ContextCompat.getDrawable(view.context, drawableId)?.apply {
                colorInt?.let {
                    setTintList(ColorStateList.valueOf(it))
                    setTintMode(tintMode)
                }
            }?.toBitmap()

            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }
}
