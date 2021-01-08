package com.hencoder.text.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.hencoder.text.R
import com.hencoder.text.dp

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp

class SportView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        /*
         * Font Size：
         * - sp：文字專用單位，其會隨著使用者手機系統的設定而改變大小。
         * - dp：與像素、密度有關的單位，由於繪製文字的特性偏向圖片，為避免跑板，因此一般繪製文字建議使用 dp。
         */
        textSize = 100.dp

        /*
         * Font Type：
         * - typeface：文字種類，例如微軟正黑體、標楷體…等。
         * - font 子集：如 ubuntu 有 ubuntu-medium、ubuntu-bold…等。
         * - isFakeBoldText：使用非原本粗體，而是使用 Android 本身的演算法加粗文字。
         */
        typeface = ResourcesCompat.getFont(context, R.font.font)
//        isFakeBoldText = true

        // 橫向居中對齊
        textAlign = Paint.Align.CENTER
    }
    private val bounds = Rect() // 僅有 top、bottom
    private val fontMetrics = Paint.FontMetrics()// 有 top、ascend、descend、bottom

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制环
        paint.style = Paint.Style.STROKE // 中空模式
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND // 線條兩端頭的樣式
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        // 上下置中：以基準線為底（預設）
//        paint.style = Paint.Style.FILL
//        canvas.drawText("abab", width / 2f, height / 2f, paint)

        // 上下置中：以文字邊界並扣除偏移量（完全置中），但由於文字邊界會因為文字不同有高低差，因此視覺上會有上下漂移的感覺；不適合動態問字。
//        paint.style = Paint.Style.FILL
//        // 藉由 getTextBounds 來獲得文字邊界，須傳入 Rect()。
//        paint.getTextBounds("abab", 0, "abab".length, bounds)
//        // 高度減去高度偏移量：(bounds.top + bounds.bottom) / 2
//        canvas.drawText(
//            "abab", width / 2f, height / 2f - (bounds.top + bounds.bottom) / 2, paint
//        )

        // 上下置中：以文字區間邊界並扣除偏移量（相對置中），與文字無關，因此不會因為文字不同而有差異，適合動態文字。
//        paint.style = Paint.Style.FILL // 填充模式
//        // 藉由 fontMetrics 來獲取文字區間。
//        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(
//            "aaaa",
//            width / 2f,
//            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f,
//            paint
//        )

        // 向上對齊、貼左
        paint.textSize = 150.dp
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
//        paint.getTextBounds( // 獲取文字邊界
//            "abab",
//            0,
//            "abab".length,
//            bounds // 傳入 Rect() 用於存放結果
//        )
        // 建議用 top，用 ascent 在某些文字時可能被切邊。
        canvas.drawText("abab", 0f, -fontMetrics.top, paint)
//        canvas.drawText("abab", 0f, -bounds.top.toFloat(), paint)


        // 處理文字與邊界的間隙。
        paint.textSize = 15.dp
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        // 若到底還有一點點縫隙，其實那是屬於文字的自然邊界，也就是說該縫隙在系統的判定中屬於文字，因此，我們無法解決。
        canvas.drawText("abab", -bounds.left.toFloat(), -bounds.top.toFloat(), paint)
    }
}