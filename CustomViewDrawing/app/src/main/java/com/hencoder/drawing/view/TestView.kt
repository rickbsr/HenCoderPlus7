package com.hencoder.drawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View


private val RADIUS = 150f.px // 使用 Kotlin 的 Extension 取代 dp2px()

class TestView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    // ANTI_ALIAS_FLAG，抗鋸齒；預設的 Paint() 是不具有此功能的，但多數時候，我們都會啟用它。
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    lateinit var pathMeasure: PathMeasure

    // 當尺寸改變的時候，繪製路徑也會改變，因此在此初始化 Path() 比在 onDraw() 更合適。
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        path.addCircle(
            width / 2f,
            height / 2f,
            RADIUS,
            /*
             * 繪製方向：其順時針與逆時針對於單一圖層沒有影響，其影響在於兩圖層交會時，例如判斷是填充還是留空。
             * - Direction.CW：Clockwise，順時針
             * - Path.Direction.CCW，逆時針
             */
            Path.Direction.CCW
        )

        path.addCircle(
            width / 2f,
            height / 2f,
            RADIUS * 1.5f,
            Path.Direction.CW
        )

        path.addRect(
            width / 2f - RADIUS, // 左邊：中心值 - 半徑
            height / 2f, // 上邊：中心值
            width / 2f + RADIUS, // 右邊：中心值 + 半徑
            height / 2f + 2 * RADIUS, // 下邊：中心值 + 2 倍半徑
            Path.Direction.CCW
        )

        // PathMeasure 是用於測量 Path 的，因此，需要在 Path 準備好以後。
        pathMeasure = PathMeasure(
            path,
            false // 是否要自動閉合，即連接處是否要計算在內。
        )

        pathMeasure.length // 或許 Path 長度
//        pathMeasure.getPosTan() // 取得目標長度時的切角值，用於計算角度。

        /*
         * 填充規則：
         * - WINDING（默認配置）：從該區域畫一條線至外面，途中與圖形的交叉點，計算方向相反的穿插次數相等則為內部，不等則為外部；因此與圖形的繪製方向，即順時針、逆時針有關。
         * - EVEN_ODD： 從該區域畫一條線至外面，途中與圖形的交叉次數，奇數是內部、偶數是外部；因此與圖形的繪製方向無關。
         * - INVERSE_WINDING：WINDING 的反規則。
         * - INVERSE_EVEN_ODD：EVEN_ODD 的反規則。
         *
         * - 備註：切點忽略不以計算。
         */
        path.fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas) {

        // 畫線
//        canvas.drawLine(100f, 100f, 200f, 200f, paint)

        // 畫圓
//        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 自定義繪製
        canvas.drawPath(path, paint)
    }
}