package com.hencoder.animation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hencoder.animation.dp

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var radius = 50.dp
        set(value) {
            field = value
            invalidate() // 要覆寫 setter 並加入 invalidate() 來重繪。
        }

    init {
        paint.color = Color.parseColor("#00796B")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

    /*
     * 在 Java ，要寫 Setter，如下：
     *
     * public void setRadius(float value) {
     *      this.radius = value;
     *      invalidate();
     * }
     */
}