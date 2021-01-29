package com.hencoder.viewgroup.view

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TwoPager(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    private var scrolling = false
    private val overScroller: OverScroller = OverScroller(context)
    private val viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain() // 初始化
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 給所有子 View 一個統一的寬高限制
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean { // 初始、計算搶奪
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear() // 清除
        }
        velocityTracker.addMovement(event) // 更新
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> if (!scrolling) {
                val dx = downX - event.x
                if (abs(dx) > pagingSlop) { // 滑動距離觸發攔截
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true) // 告訴父 View 不要攔截
                    result = true
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> { // 各種紀錄
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - event.x + downScrollX).toInt()
                        .coerceAtLeast(0)
                        .coerceAtMost(width)
                scrollTo(dx, 0) // 對所有內容進行偏移，但是是反向（不同物理模型導致）
            }
            MotionEvent.ACTION_UP -> {
                // 計速器，單位：1000 毫秒內移動的像素數量、上限
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat()) // 最大的快滑速度
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) { // 最小的快滑速度
                    if (scrollX > width / 2) 1 else 0 // 吸附型停靠
                } else {
                    if (vx < 0) 1 else 0 // 快華型停靠
                }
                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0) // startScroll 要求到達時剛好為速度 0
                postInvalidateOnAnimation() // 讓下一幀讓頁面標記為失效
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY) // 反向思維
            postInvalidateOnAnimation() // 失效後就會調用 draw()，成循環。
        }
    }
}
