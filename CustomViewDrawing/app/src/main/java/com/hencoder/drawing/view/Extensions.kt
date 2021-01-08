package com.hencoder.drawing.view

import android.content.res.Resources
import android.util.TypedValue

/**
 * In Kotlin，以擴展取代「Utils」的「dp2px()」
 */
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )