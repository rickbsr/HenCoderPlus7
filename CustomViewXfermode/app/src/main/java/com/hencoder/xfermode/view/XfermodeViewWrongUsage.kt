package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.px


/**
 * PorterDuff.Mode
 *
 * - 官方說明文件：
 * - https://developer.android.com/reference/android/graphics/PorterDuff.Mode
 *
 * - 錯誤原因說明：
 * - 因為無論是「Source image」或「Destination image」都是包含著背景的圖片。
 * - 本範例的設置僅會依據「Source image」的區域判斷，而不會去處理「Destination image」獨立於「Source image」的那區域，才會造成「SRC」、「SRC_IN」的結果與預期不同。
 */
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC)

class XfermodeViewWrongUsage(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bounds, null)

        // 畫粉色圓形
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(200f.px, 50f.px, 300f.px, 150f.px, paint)

        paint.xfermode = XFERMODE

        // 畫藍色方形
        paint.color = Color.parseColor("#2196F3")
        canvas.drawRect(150f.px, 100f.px, 250f.px, 200f.px, paint)

        paint.xfermode = null
        canvas.restoreToCount(count)
    }
}