package com.hencoder.scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.hencoder.scalableimageview.dp
import com.hencoder.scalableimageview.getAvatar
import kotlin.math.max
import kotlin.math.min

private val IMAGE_SIZE = 300.dp.toInt()

private const val EXTRA_SCALE_FACTOR = 1.5f // 縮放係數

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, // 手勢監聽
    GestureDetector.OnDoubleTapListener, // 雙擊監聽
    Runnable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getAvatar(resources, IMAGE_SIZE)

    // 初始位置
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    // 偏移
    private var offsetX = 0f
    private var offsetY = 0f

    private var smallScale = 0f
    private var bigScale = 0f

    private var big = false

    private val gestureDetector =
        GestureDetectorCompat(context, this)
//            .apply {
//                /*
//                 * 若目標 View 為 OnGestureListener，又同時為 OnDoubleTapListener，則
//                 * OnDoubleTapListener 會自動被觸發（源碼中）；因此後續可以省略。
//                 */
//                setOnDoubleTapListener(this@ScalableImageView)
//            }

    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate() // 觸發畫面重繪製
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    // 滑動計算器
    private val scroller = OverScroller(context) // 有初始速度，適合慣性滑動
//    private val scroller = Scroller(context) // 像從 0 啟動

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 將圖片置中
        originalOffsetX = (width - IMAGE_SIZE) / 2f
        originalOffsetY = (height - IMAGE_SIZE) / 2f

        // 縮放邏輯
        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event) // 置換監聽的功能
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 可以關閉長按支持
//        gestureDetector.setIsLongpressEnabled(false)

//        val scale = if (big) bigScale else smallScale

        /*
         * 偏移量：
         *
         * 若順向思考，其偏移量需要考量 Canvas 座標系改變的情況，因次，需要除以 EXTRA_SCALE_FACTOR，
         * 但是若逆向思考，則可以省略。
         */
        canvas.translate(offsetX, offsetY)

        val scale = smallScale + (bigScale - smallScale) * scaleFraction // 更改區間

        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    /*
     * 若我們要拿到事件，此處必須返回 true；事實上，此處幾乎橫等於 true。
     *
     * - return true
     */
    override fun onDown(e: MotionEvent): Boolean {
        return true // e.actionMasked == MotionEvent.ACTION_DOWN 亦可。
    }

    /*
     * 當用戶觸摸到且持續 100 毫秒；若支持長按監聽，則若按住超過 500 毫秒也不會觸發。
     */
    override fun onShowPress(e: MotionEvent?) {
    }

    /*
     * 單次按下抬起，就是點擊，基本上類似於 onClick，雖然仍有區別，但可作為替代；此外，返回值代表是否消費點擊事件，
     * 但要注意的是，此外的返回值僅提供系統紀錄用，是不具任何效果的。
     *
     * 備註：決定是否消費事件，是在 onDown 的返回值中決定。
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    /*
     * 此處的 onScroll 比較像是 onMove，也就是手指開始移動的時候。
     */
    override fun onScroll(
        downEvent: MotionEvent?, // Down 事件
        currentEvent: MotionEvent?, // 當前事件
        distanceX: Float, distanceY: Float // 距離上次事件的距離，但要注意的是，此處是「舊位置 - 新位置」。
    ): Boolean { // 返回值同樣是表示是否消費該事件，但僅供紀錄用，不具實際作用。

        if (big) {
            // 計算位移
            offsetX -= distanceX
            offsetY -= distanceY

            // 限制位移
            offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
            offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)

            invalidate()
        }
        return false
    }

    /*
     * 長按觸發。
     */
    override fun onLongPress(e: MotionEvent?) {
    }

    /*
     * 快速滑動（慣性滑動），手只會離開屏幕。
     */
    override fun onFling(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        velocityX: Float, velocityY: Float // 位移
    ): Boolean {
        if (big) {
            // 滑動參數須依照需求跟模型設置
            scroller.fling(
                offsetX.toInt(), offsetY.toInt(), // 中心點
                velocityX.toInt(), velocityY.toInt(), // 速度
                (-(bitmap.width * bigScale - width) / 2).toInt(),
                ((bitmap.width * bigScale - width) / 2).toInt(),
                (-(bitmap.height * bigScale - height) / 2).toInt(),
                ((bitmap.height * bigScale - height) / 2).toInt(),
                40.dp.toInt(), 40.dp.toInt() // 過度滑動
            )

            // 動畫呈現，比較生硬的方式
//            for (i in 10..100 step 10) {
//                postDelayed({ refresh() }, i.toLong())
//            }

            // 建議使用 postOnAnimation 取代 forLoop
//            postOnAnimation(this) // 一幀一幀推送，但 API 須大於 16。
//            post(this) // 效果大致類似，但是推送比較快，可能一幀推送完。

            // 最建議使用，安全，詳見源碼
            ViewCompat.postOnAnimation(this, this)
        }
        return false
    }

    override fun run() {
        if (scroller.computeScrollOffset()) { // 記住此時狀態
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()

            // 藉由 scroller.computeScrollOffset() 的回傳來判斷動畫狀態，避免無窮重複刷新。
            ViewCompat.postOnAnimation(this, this)
        }
    }

    /*
     * 精準單擊判斷：
     *
     * 當支持雙擊時，若使用 onShowPress() 作為單擊判斷，會因為快速雙擊同時也可能滿足觸發 onShowPress() 的條件，
     * 而導致其判斷可能不夠準確，因此必須使用此方法來判定單擊。
     *
     * 備註：若不支持雙擊，盡量避免用 onSingleTapConfirmed()，因為 onSingleTapConfirmed() 有延遲，此外，
     * 由於其排除一些條件，所以在不支持雙擊時，用 onShowPress() 涵蓋性更好，更貼近用戶預期。
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    /*
     * 快速雙擊觸發：
     *
     * - 在「第一次按下 + 第一次抬起 + 第二次按下」就會觸發；兩次按下間隔必須在 300 毫秒。
     * - 防止手抖動，若兩次按下間隔少於 40 毫秒，同樣不會觸發。
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean { // 返回值同樣沒有實際作用
        big = !big

        if (big) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }

//        invalidate() // 觸發重繪製
        return true
    }

    /*
     * 雙擊後續事件都會觸發，例如雙擊的第二下按住拖動等。
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }
}
