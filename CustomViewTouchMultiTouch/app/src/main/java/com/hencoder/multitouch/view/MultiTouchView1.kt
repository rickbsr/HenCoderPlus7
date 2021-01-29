package com.hencoder.multitouch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hencoder.multitouch.dp
import com.hencoder.multitouch.getAvatar

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
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
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0) // 取得手指 id。

                downX = event.x
                downY = event.y

                // 紀錄偏移位置，為下一次的初始偏移
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_DOWN -> { // 非第一根手指按下
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)

                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)

                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_UP -> { // 非最後一根手指抬起
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                if (pointerId == trackingPointerId) { // 若是目標手指抬起
                    // 因為所有的手指是包含正在抬起的那跟手指
                    val newIndex =
                        if (actionIndex == event.pointerCount - 1) { // 若抬起手指為 index 最大的那根手指
                            event.pointerCount - 2
                        } else {
                            event.pointerCount - 1
                        }
                    trackingPointerId = event.getPointerId(newIndex)

                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)

                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId) // 取得追蹤手指的 index

                // 校正滑動軸心 = 目前位置 - 手指位置 + 初始偏移位置
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}