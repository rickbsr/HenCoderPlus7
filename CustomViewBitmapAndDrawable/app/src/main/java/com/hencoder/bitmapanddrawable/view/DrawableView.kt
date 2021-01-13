package com.hencoder.bitmapanddrawable.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import com.hencoder.bitmapanddrawable.dp
import com.hencoder.bitmapanddrawable.drawable.MeshDrawable

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //    private val drawable = ColorDrawable(Color.RED)
    private val drawable = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Drawable 默認範圍是 (0, 0)，是空的。
        drawable.setBounds(50.dp.toInt(), 80.dp.toInt(), width, height)
        /*
         * 為什麼 Drawable 不是 canvas.drawDrawable(drawable) 這樣撰寫？
         * 因為 Drawable 更像是小型、功能單一、僅能繪製的 View，Drawable 所存放的是一套繪製代碼或繪製規則等，而 Bitmap 就只是圖片的存放訊息。
         */
        drawable.draw(canvas)
    }
}