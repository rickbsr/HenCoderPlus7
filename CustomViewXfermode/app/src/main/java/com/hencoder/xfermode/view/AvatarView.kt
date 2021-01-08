package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.R
import com.hencoder.xfermode.px

private val IMAGE_WIDTH = 200f.px
private val IMAGE_PADDING = 20f.px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        // 離屏緩衝（抽出）
        val count = canvas.saveLayer(
            bounds, // 所需要的區域
            null
        )

        // 畫圓，橢圓是利用矩形為基準而繪製，在具有邊常的情況下，用以繪製圓形更為適合。
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH,
            paint
        )

        /*
         * 目前僅剩的「XferMode」方式：PorterDuff.Mode
         *
         * 參數設定請參考：
         * https://developer.android.com/reference/android/graphics/PorterDuff.Mode#alpha-compositing-modes
         */
        paint.xfermode = XFERMODE

        // 獲取頭像
        canvas.drawBitmap(
            getAvatar(IMAGE_WIDTH.toInt()),
            IMAGE_PADDING,
            IMAGE_PADDING,
            paint
        )

        paint.xfermode = null

        // 離屏緩衝（還回）
        canvas.restoreToCount(count)
    }

    private fun getAvatar(width: Int): Bitmap {
        // BitmapFactory.Options() 與圖片解碼相關，可以用來調整載入圖片的大小，以優畫圖片加載
        val options = BitmapFactory.Options()

        // 第一次加載，藉由 inJustDecodeBounds =true，讀取圖片資訊邊界、大小等，但不實際獲取圖片本身。
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)

        // 第二次加載，藉由 inJustDecodeBounds =false，實際獲取圖片。
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}