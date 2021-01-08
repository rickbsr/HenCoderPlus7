package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.R
import com.hencoder.xfermode.px

/**
 * PorterDuff.Mode
 *
 * - 官方說明文件：
 * - https://developer.android.com/reference/android/graphics/PorterDuff.Mode
 *
 * - 正確的操作方式：
 * - 為了達到預期效果，我們必須將「Destination image」的區域也包入。
 *
 */
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

class XfermodeView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)

    /*
     * 重新建立 Bitmap。
     */
    private val circleBitmap =
        Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)
    private val squareBitmap =
        Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)

        paint.color = Color.parseColor("#2196F3")
        canvas.setBitmap(squareBitmap)
        canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
    }

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bounds, null)

        canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)

        paint.xfermode = XFERMODE

        canvas.drawBitmap(squareBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = null

        canvas.restoreToCount(count)
    }
}