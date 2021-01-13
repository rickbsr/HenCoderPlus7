package com.hencoder.bitmapanddrawable

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Kotlin Ktx 的擴展函數提供 toDrawable() 和 toBitmap()
         *
         * 事實上，Bitmap 與 Drawable 並不是相同的東西，Bitmap 僅是個儲存工具，而 Drawable 則是一套繪製規則。
         * 因此，Bitmap 與 Drawable 之間的轉換，並不是意義上的直接互轉。
         *
         * toDrawable()：其實將 Bitmap 裝到 Drawable 內。
         * toBitmap()：創見一張 Bitmap，在用 Drawable 的規則將之繪出；若該 Drawable 是 BitmapDrawable，直接取出即可。
         *
         */
//        val bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)
//        bitmap.toDrawable(resources)
//        val drawable = ColorDrawable()
//        drawable.toBitmap()
    }
}