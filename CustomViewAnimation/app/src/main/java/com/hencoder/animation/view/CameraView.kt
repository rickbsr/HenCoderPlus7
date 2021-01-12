package com.hencoder.animation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.hencoder.animation.R
import com.hencoder.animation.dp

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val camera = Camera()

    // 頂部翻起
    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }

    // 底部翻起
    var bottomFlip = 30f
        set(value) {
            field = value
            invalidate()
        }

    // 摺痕角度
    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        camera.setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        // 上半部分
        canvas.withSave {
            canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
            canvas.rotate(-flipRotation)

            camera.save() // 每當我們重新進入一次 Activity 時， onDraw 都會在重新呼叫一次，因此 camera 要儲存。
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore() // 回復 camera 的角度。

            canvas.clipRect(
                -BITMAP_SIZE, -BITMAP_SIZE,
                BITMAP_SIZE, 0f
            )
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(
                bitmap,
                BITMAP_PADDING,
                BITMAP_PADDING, paint
            )
        }

        // 下半部分
        canvas.withSave { // .withSave{} 是 Kotlin 擴展函數用以取代 save(), restore()
            canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(bottomFlip) // 旋轉角度，重繪過程中會一直改動。
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(
                -BITMAP_SIZE, 0f,
                BITMAP_SIZE,
                BITMAP_SIZE
            )
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(
                bitmap,
                BITMAP_PADDING,
                BITMAP_PADDING, paint
            )
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(
            resources,
            R.drawable.avatar_rengwuxian, options
        )
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(
            resources,
            R.drawable.avatar_rengwuxian, options
        )
    }
}