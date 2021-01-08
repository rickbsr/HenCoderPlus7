package com.hencoder.drawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private const val OPEN_ANGLE = 120f
private const val MARK = 5
private val RADIUS = 150f.px
private val LENGTH = 120f.px
private val DASH_WIDTH = 2f.px
private val DASH_LENGTH = 10f.px

class DashboardView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dash = Path()
    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE // 預設為實心，將之設為空心模式。

        // 刻度表的效果：以矩形，若其一邊與弧線為切線，則其垂直邊就為割線，恰為儀表板的刻度方向。
        dash.addRect(
            0f, // 左
            0f, // 上
            DASH_WIDTH, // 右：此處模擬刻度的寬度
            DASH_LENGTH, // 下：此處模擬刻度的長度
            Path.Direction.CCW
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        /*
         * 畫弧線的 Path 是與畫面相關，因此應該寫在 onSizeChanged()，而非 init 區塊。
         */
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )

        val pathMeasure = PathMeasure(path, false)

        /*
         * 藉由 PathMeasure() 計算刻度間的間隔距離：理論上，是 PathMeasure() / 間隔數目，但由於刻點的數目會比間隔多 1，因此，若以「路徑全長」去計算，則最後一個刻點會無法繪製，因此我們必須減從第一個刻度的寬度以後開始計算，換句話說，就是必須減去一個刻度寬度。
         */
        fun getAdvance(intervals: Int): Float = (pathMeasure.length - DASH_WIDTH) / intervals

        pathEffect =
            PathDashPathEffect( // 假設目標 Path 為虛線，並用 Path 的效果取代其虛線點。
                dash,
                /*
                 * 備註：在此處有一「BUG」，「Advance」是前置量，「Phase」是間隔，但「Android」將兩者設計相反了，因此，我們在使用時，記得也要反過來。
                 */
                getAdvance(20), // Bug：此處的值是「間隔」
                0f, // Bug：此處的值是「前置量」
                PathDashPathEffect.Style.ROTATE
            )
    }

    override fun onDraw(canvas: Canvas) {
        // 畫刻度，因為武們想要藉由 PathMeasure() 來計算刻度距離，因此改用 drawPath()
        canvas.drawPath(path, paint)
//        canvas.drawArc(
//            width / 2f - RADIUS,
//            height / 2f - RADIUS,
//            width / 2f + RADIUS,
//            height / 2f + RADIUS,
//            90 + OPEN_ANGLE / 2f, // 起始角度
//            360 - OPEN_ANGLE, // 經過角度
//            false, // 是否連接圓中心
//            paint
//        )

        // 添加刻度的效果
        paint.pathEffect = pathEffect

        // 畫弧線
        canvas.drawPath(path, paint)

        // 取消添加的效果
        paint.pathEffect = null

        // 畫指針
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LENGTH * cos(markToRadians(MARK)).toFloat(),
            height / 2f + LENGTH * sin(markToRadians(MARK)).toFloat(),
            paint
        )
    }

    private fun markToRadians(mark: Int): Double {
        val startAngle = 90 + OPEN_ANGLE / 2f // 起始角度
        val throughAngle = (360 - OPEN_ANGLE) / 20f * mark // 劃過角度，「mark」代表指針目前的位置在的刻度。

        return Math.toRadians( // 將角度值轉為弧度值
            (startAngle + throughAngle).toDouble() // 總角度
        )
    }
}