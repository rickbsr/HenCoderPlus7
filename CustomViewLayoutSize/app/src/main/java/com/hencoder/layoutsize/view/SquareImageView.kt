package com.hencoder.layoutsize.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * 簡單改寫已有的 View 尺寸
 */
class SquareImageView(context: Context?, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    /*
     * 在 Layout 中強行修改子 View 的尺寸：
     *
     * 雖然有效，但應避免這類強制衝突的修改方式；但此處的操作會使父 View 無法得知子 View 的實際佈局邊界，
     * 而當 View 父無法掌控全局的佈局時，就可能會出現非預期的情況。
     *
     * 結論：勁量避免覆寫 layout()。
     */
//    override fun layout(l: Int, t: Int, r: Int, b: Int) {
//        val width = r - l
//        val height = b - t
//        val size = min(width, height)
//        super.layout(l, t, l + size, t + size)
//    }

    /*
     * 自定義 View 尺寸。
     *
     * - Step1. 藉由 getMeasuredWidth() 和 getMeasuredHeight() 來獲取量測到的尺寸。
     * - Step2. 藉由 setMeasuredDimension() 來設置自定義尺寸。
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }
}