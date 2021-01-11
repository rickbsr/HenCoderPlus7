package com.hencoder.text.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.hencoder.text.R
import com.hencoder.text.dp

private val IMAGE_SIZE = 150.dp
private val IMAGE_PADDING = 90.dp

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    // Lorem Ipsum：沒意義的文字，方便測試使用。
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tristique urna tincidunt maximus viverra. Maecenas commodo pellentesque dolor ultrices porttitor. Vestibulum in arcu rhoncus, maximus ligula vel, consequat sem. Maecenas a quam libero. Praesent hendrerit ex lacus, ac feugiat nibh interdum et. Vestibulum in gravida neque. Morbi maximus scelerisque odio, vel pellentesque purus ultrices quis. Praesent eu turpis et metus venenatis maximus blandit sed magna. Sed imperdiet est semper urna laoreet congue. Praesent mattis magna sed est accumsan posuere. Morbi lobortis fermentum fringilla. Fusce sed ex tempus, venenatis odio ac, tempor metus."

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    private val bitmap = getAvatar(IMAGE_SIZE.toInt())
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        // 在新版本已經改用 Builder 方式。
//        val staticLayout = StaticLayout(
//            text,
//            textPaint,
//            width, // 寬度
//            Layout.Alignment.ALIGN_NORMAL, // 對齊方式
//            1f, // 文字間距調整（乘除），為原始間距的倍數，1 代表不改變。
//            0f, // 文字間距調整（加減）。
//            false // 行與行之間是否需要額外間距。
//        )

        // 以 Builder 方式。
//        val staticLayout = StaticLayout.Builder
//            .obtain(text, 0, text.length, textPaint, width)
//            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
//            .build()

//        staticLayout.draw(canvas)

        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)
        paint.getFontMetrics(fontMetrics)

        /*
        val measuredWidth = floatArrayOf(0f)

        // 第一行
        var count = paint.breakText( // breakText() 可以判斷能寫多少文字，其返回值為到哪個文字。
            text,
            true, // 測量文字的方向，順向：true，逆向：false。
            width.toFloat(), // 要撰寫的寬度。
            measuredWidth // 測量寬度。
        )
        canvas.drawText(text, 0, count, 0f, -fontMetrics.top, paint)

        // 第二行
        val oldCount = count
        count = paint.breakText(text, count, text.length, true, width.toFloat(), measuredWidth)
        canvas.drawText(
            text, oldCount, oldCount + count, 0f,
            -fontMetrics.top + paint.fontSpacing, // 折行的方式可以用 * 2，但更建議的方式是 paint.fontSpacing 。
            paint
        )
         */
        val measuredWidth = floatArrayOf(0f)

        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top // 初始偏移
        var maxWidth: Float // 可繪製文字的最大寬度

        while (start < text.length) { // 至文字繪製結束。

            // 判斷可繪製的寬度
            maxWidth = if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING || // 頂部
                verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE // 底部
            ) {
                width.toFloat()
            } else {
                width.toFloat() - IMAGE_SIZE
            }

            count = paint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)

            start += count // 刷新文字起始點
            verticalOffset += paint.fontSpacing // 換行。
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}