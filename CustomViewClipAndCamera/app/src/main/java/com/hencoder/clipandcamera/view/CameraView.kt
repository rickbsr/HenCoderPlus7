package com.hencoder.clipandcamera.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.clipandcamera.R
import com.hencoder.clipandcamera.dp

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val clipped = Path().apply {
        addOval(
            BITMAP_PADDING,
            BITMAP_PADDING,
            BITMAP_PADDING + BITMAP_SIZE,
            BITMAP_PADDING + BITMAP_SIZE,
            Path.Direction.CCW
        )
    }
    private val camera = Camera()

    init {
        camera.rotateX(30f)

        // 藉由調整 Camera 的位置，來縮小視覺幅度。
        camera.setLocation(
            0f, 0f,
            // 單位是英吋，等於 72 像素，默認值是 -8，其值與像素密度相關。
            -6 * resources.displayMetrics.density
        )
    }

    override fun onDraw(canvas: Canvas) {
        // 切矩形
//        canvas.clipRect(
//            BITMAP_PADDING,
//            BITMAP_PADDING,
//            BITMAP_PADDING + BITMAP_SIZE / 2,
//            BITMAP_PADDING + BITMAP_SIZE / 2
//        )

        // clipPath() 是精確的切割，因此並沒有經過反鋸齒修飾，此外，這是特定，無法改變。
//        canvas.clipPath(clipped)
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)

        /*
         * 由於 Canvas 座標係是獨立的存在，所以在繪製時，須以 Canvas 座標係整體為思考。
         *
         * 案例需求：
         * - Step 1. 先往右平移 300。
         * - Step 2. 順時針旋轉 45 度。
         */
        // 順向思考：因為 Canvas 座標向右平移 200，因此旋轉軸心也向右平移 200。
//        canvas.translate(200.toFloat(), 0.toFloat())
//        canvas.rotate(45.toFloat(), 200 + BITMAP_SIZE / 2, BITMAP_SIZE / 2) // 非預期結果，因爲軸心不對。
//        canvas.rotate(45.toFloat(), BITMAP_SIZE / 2, BITMAP_SIZE / 2) // 預期結果，將軸心向左回移。
//        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), paint)

        // 逆向思考（預期結果）：先旋轉再平移。
//        canvas.rotate(45.toFloat(), 200 + BITMAP_SIZE / 2, BITMAP_SIZE / 2)
//        canvas.translate(200.toFloat(), 0.toFloat())
//        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), paint)

        /*
         * Camera()：
         *
         * - 是三維的成像，X 軸由左至右，Y 軸由上至上，Z 軸由螢幕外向螢幕內，預設軸心是 (0,0,0)。
         * - 是由外向內穿透成像。
         *
         * 案例需求：
         * - 向上翻轉 30。
         *
         */
        // 直接撰寫（非預期結果）：因為軸心是(0,0,0)，所以成像與預期偏移。
//        camera.applyToCanvas(canvas)
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)

        // 順向思考（非預期結果）：移動 Canvas 座標係至 (0,0,0)，翻轉後再移動回來，但問題是 Camera 在移動的過程中也會跟著移動。
//        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
//        camera.applyToCanvas(canvas)
//        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)

        // 逆向思路（預期結果）：
//        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
//        camera.applyToCanvas(canvas)
//        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)

        /*
         * 翻頁效果，具體作法是將圖片分為上下兩塊處理。
         *
         * 案例需求：
         * - 垂直切。
         */
        // 上半部分
//        canvas.save()
//        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
//        canvas.clipRect( // 切割要在旋轉之前
//            -BITMAP_SIZE / 2,
//            -BITMAP_SIZE / 2,
//            BITMAP_SIZE / 2,
//            0f
//        )
//        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
//        canvas.restore()

        // 下半部分
//        canvas.save()
//        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
//        camera.applyToCanvas(canvas)
//        canvas.clipRect( // 切割要在旋轉之前
//            -BITMAP_SIZE / 2,
//            0f, // 裁掉
//            BITMAP_SIZE / 2,
//            BITMAP_SIZE / 2
//        )
//        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
//        canvas.restore()

        /*
         * 翻頁效果，具體作法是將圖片分為上下兩塊處理。
         *
         * 案例需求：
         * - 斜切。
         */
        // 上半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.rotate(-20f) // 切後，回覆旋轉
        canvas.clipRect( // 因為旋轉後，切的範圍會變小，所以要擴大，理論上是 1.414..，即根號二，此處，方便操作，以 2 被為例。
            -BITMAP_SIZE,
            -BITMAP_SIZE,
            BITMAP_SIZE,
            0f
        )
        canvas.rotate(20f) // 在移動後，切之前，旋轉
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()

        // 下半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.rotate(-20f) // 切後，回覆旋轉
        camera.applyToCanvas(canvas)
        canvas.clipRect( // 切割要在旋轉之前
            -BITMAP_SIZE,
            0f, // 裁掉
            BITMAP_SIZE,
            BITMAP_SIZE
        )
        canvas.rotate(20f) // 在移動後，切之前，旋轉
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2))
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
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