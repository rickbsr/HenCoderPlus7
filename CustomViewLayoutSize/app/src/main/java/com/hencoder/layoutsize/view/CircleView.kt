package com.hencoder.layoutsize.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hencoder.layoutsize.dp

/**
 * 完全自定義 View 尺寸
 */
private val RADIUS = 100.dp
private val PADDING = 100.dp

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((PADDING + RADIUS) * 2).toInt()

        /*
         * 需以父 View 的要求作為限制：
         *
         * - 父類的限制會以參數的方式傳入，如 widthMeasureSpec 與 heightMeasureSpec；其包含資訊有限制模式、
         *   大小與精確值。
         * - 限制的模式主要有三類：
         *   1. 精準的限制
         *   2. 範圍區間
         *   3. 不限制
         */
//        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec) // 取得限制模式
//        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val width = when (specWidthMode) {
//            MeasureSpec.EXACTLY -> specWidthSize // 精確值，即測量值沒用，用 spec 值
//            MeasureSpec.AT_MOST -> // 範圍區間
//                if (size > specWidthSize) specWidthSize else size
//            else -> size // MeasureSpec.UNSPECIFIED, 不限制。
//        }

        /*
         * resolveSize()：
         *
         * 上述的流程方法就是 resolveSize() 的實作。
         */
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)

        /*
         * resolveSizeAndState()：
         *
         * 比起 resolveSize()，在 MeasureSpec.AT_MOST 模式且 specSize 小於 size 的情況下，
         * 其會給予 MEASURED_STATE_TOO_SMALL 標籤，它就可以向父 View 要求重新測量。
         *
         * 結論：理論上，resolveSizeAndState() 是一個理想的方式，但實際上，大多數的父 View 都有沒實作該功能，
         * 也就是說，他們並不會去讀取 MEASURED_STATE_TOO_SMALL 標籤，因此 resolveSize() 是一個比較建議的用法。
         */
//        val width = resolveSizeAndState()

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }
}