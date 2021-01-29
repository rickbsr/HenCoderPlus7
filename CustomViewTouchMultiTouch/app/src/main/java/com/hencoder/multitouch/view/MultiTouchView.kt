package com.hencoder.multitouch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hencoder.multitouch.dp
import com.hencoder.multitouch.getAvatar

class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp.toInt())

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        /*
         * MotionEvent 包含的訊息有 x、y、index、id。
         *
         * - x/y：代表位置
         * - index：代表第幾跟手指
         * - id：代表手機 ID
         *
         * index 與 id 的概念是不同的，假設操作是如下：食指按下、然後中指按下，
         *
         * Step 1. 食指按下：
         * - 此時，ACTION_DOWN 傳入的資訊，食指的 index 是 0。
         *
         * Step 2. 中指再按下：
         * - 食指 index 0，中指 index 1，兩訊息會同時被傳入。
         *
         * Step 3-1：
         * - 中指抬起，食指 index 0，沒有中指的訊息。
         *
         * Step 3-2：
         * - 食指抬起，中指為 index 0，沒有食指的訊息。
         *
         * Step 3-1-1：
         * - 中指放下，食指 index 0，中指 index 1，兩訊息會同時被傳入。
         *
         * Step 3-2-1（原本遞補的中指又變回 index 1）：
         * - 食指放下，食指 index 0，中指 index 1，兩訊息會同時被傳入。
         *
         * 而 id 的歸則是，只要手指不離開螢幕，就不會改變。
         *
         * 總結：index 用於遍歷，id 用於追蹤。
         *
         */
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y

                // 取得 Id
//                event.getPointerId()

                // 紀錄偏移位置，為下一次的初始偏移
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {

                /*
                 * event.x/y 所獲取的是 index = 0 的手機；要取得其它 index 的手指，需要輸入參數。
                 *
                 * - getX() 等於 getX(0)
                 * - getY() 等於 getY(0)
                 */
//                for (i in 0 until event.pointerCount) {
//                    event.getX(i)
//                    event.getY(i)
//                }

                // 藉由 Id 取得，Id 可以在 DOWN 取得
//                event.findPointerIndex(downId)

                /*
                 * event.actionIndex
                 *
                 * 可以取得當前動作的手指 index，如按下、抬起時；但是對於 MOVE 時，其總是返回 0，
                 * 這是因為對 MOVE 而言並沒有那個所謂總是在移動的手指，因此，
                 * event.actionIndex 在 MOVE 中使用是沒有意義的。
                 *
                 * 總結：event.actionIndex 僅在 ACTION_POINTER_DOWN/UP 中才有意義。
                 */
//                event.actionIndex

                // 校正滑動軸心 = 目前位置 - 手指位置 + 初始偏移位置
                offsetX = event.x - downX + originalOffsetX
                offsetY = event.y - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                trackingPointerId = event.getPointerId(0)
//                downX = event.x
//                downY = event.y
//                originalOffsetX = offsetX
//                originalOffsetY = offsetY
//            }
//            MotionEvent.ACTION_POINTER_DOWN -> {
//                val actionIndex = event.actionIndex
//                trackingPointerId = event.getPointerId(actionIndex)
//                downX = event.getX(actionIndex)
//                downY = event.getY(actionIndex)
//                originalOffsetX = offsetX
//                originalOffsetY = offsetY
//            }
//            MotionEvent.ACTION_POINTER_UP -> {
//                val actionIndex = event.actionIndex
//                val pointerId = event.getPointerId(actionIndex)
//                if (pointerId == trackingPointerId) {
//                    val newIndex = if (actionIndex == event.pointerCount - 1) {
//                        event.pointerCount - 2
//                    } else {
//                        event.pointerCount - 1
//                    }
//                    trackingPointerId = event.getPointerId(newIndex)
//                    downX = event.getX(newIndex)
//                    downY = event.getY(newIndex)
//                    originalOffsetX = offsetX
//                    originalOffsetY = offsetY
//                }
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val index = event.findPointerIndex(trackingPointerId)
//                offsetX = event.getX(index) - downX + originalOffsetX
//                offsetY = event.getY(index) - downY + originalOffsetY
//                invalidate()
//            }
//        }
//        return true
//    }
}