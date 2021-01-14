/*
 * 使用 @file:JvmName，可以使得在 Java 中調用時取代文件名。
 */
@file:JvmName("KotlinUtils")

package com.example.core.utils

import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

private val displayMetrics = Resources.getSystem().displayMetrics

/*
 * 頂層函數，又稱為文件函數。
 * - Java：以文件名調用。
 * - Kotlin：直接調用。
 */
fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
}

object Utils {
    fun toast(string: String?) {
        toast(string, Toast.LENGTH_SHORT)
    }

    fun toast(string: String?, duration: Int) {
        Toast.makeText(BaseApplication.currentApplication(), string, duration).show()
    }
}
