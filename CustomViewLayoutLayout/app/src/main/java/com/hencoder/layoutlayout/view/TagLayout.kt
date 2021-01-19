package com.hencoder.layoutlayout.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    /*
     * 因為 ViewGroup 會針對所有的子 View 去遞迴，因此我們需要將每個子 View 的邊界儲存。
     */
    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // 已經使用掉的長寬（總長、總寬）。
        var widthUsed = 0
        var heightUsed = 0

        var lineWidthUsed = 0 // 當前行被使用的寬度
        var lineMaxHeight = 0 // 當前行的最大高度

        // 取得父 View 的要求
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        // 對所有子 View 遞迴
        for ((index, child) in children.withIndex()) {

            // 取得與 Layout 相關的參數，此處是為了取得開發者的要求
//            val layoutParams = child.layoutParams

            /*
             * 遞迴子 View 的 measure，並傳入限制的參數：MeasureSpec。
             *
             * - 子 View 的 MeasureSpec 為父 View 的限制，所以我們必須將限制傳給子 View。
             */
//            var childWidthSpecMode = 0
//            var childWidthSpecSize = 0

            /*
             * 子 View 限制的算法：
             *
             * 子 View 的限制由「開發者的要求」和「父 View 的限制」所構成，下述為通用算法。
             */
//            when (layoutParams.width) { // 開發者要求
//                LayoutParams.MATCH_PARENT -> {
//                    when (widthSpecMode) { // 父 View 的要求
//                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
//                            childWidthSpecMode = MeasureSpec.EXACTLY
//                            childWidthSpecSize = widthSpecSize - widthUsed // 總共可共空間 - 已經被使用的空間
//                        }
//                        MeasureSpec.UNSPECIFIED -> {
//                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
//                            childWidthSpecSize = 0
//                        }
//                    }
//                }
//                LayoutParams.WRAP_CONTENT -> {
//                    when (widthSpecMode) {
//                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
//                            childWidthSpecMode = MeasureSpec.AT_MOST
//                            childWidthSpecSize = widthSpecSize - widthUsed
//                        }
//                        MeasureSpec.UNSPECIFIED -> {
//                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
//                            childWidthSpecSize = 0
//                        }
//                    }
//                }
//                else -> { // 開發者給精準尺寸，直接將尺寸提供給子 View。
//                    childWidthSpecMode = MeasureSpec.EXACTLY
//                    childWidthSpecSize = layoutParams.width
//                }
//            }

            /*
             * measureChildWithMargins()：
             *
             * 而 measureChildWithMargins() 的就是實作子 View 限制的通用算法，與上述算法大同小異；
             * 不過在實際應用時，雖然通用算法可以符合多數的情況，但若遇到佈局邏輯較為特殊者，亦可自行客製所需邏輯。
             */
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            // 寬度判斷，判斷是否換行。
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                lineWidthUsed + child.measuredWidth > specWidthSize
            ) {
                lineWidthUsed = 0
                heightUsed += lineMaxHeight // 換行，總的使用高度必須加上當前最大高度。
                lineMaxHeight = 0
                // 換行時，由於參數變更需要再次測量。
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            // 若 index 等於 childrenBounds.size，則代表是新的，需要創建；事實上，此處不可能為大於。
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }

            val childBounds = childrenBounds[index]
            childBounds.set( // 將邊界保存
                lineWidthUsed, heightUsed,
                // 右、底的邊界須加上當前子 View 的長寬，此為測量階段，因此是測量值。
                lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight
            )

            lineWidthUsed += child.measuredWidth // 更新當前行的寬度
            widthUsed = max(widthUsed, lineWidthUsed) // 總的寬度
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight) // 當前行的最大高度
        }

        // 計算自己的寬、高。
        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        /*
       * 狀況：僅一個「ColoredTextView」，為「北京市」，
       * 效果：填滿整個畫面。
       *
       * 知識點：onLayout() 所傳入的 l、t、r、b，是子 View 相對於父 View 的位置。
       */
//        for (child in children) {
//            child.layout(
//                0, 0, // 左、上，要填滿應該要設定為 0。
//                r - l, // 右：子 View 的右減去子 View 的左。
//                b - t // 下：子 View 的下減去子 View 的上。
//            )
//        }

        /*
         * 狀況：二個「ColoredTextView」。
         * 效果：左上角一個，右下角一個。
         */
//        for ((index, child) in children.withIndex()) {
//            if (index == 0) {
//                child.layout(0, 0, (r - l) / 2, (b - t) / 2)
//            } else {
//                child.layout((r - l) / 2, (b - t) / 2, r - l, b - t)
//            }
//        }

        // 根據子 View 的邊界值 Layout。
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {

        // 直接產生並返回 MarginLayoutParams。
        return MarginLayoutParams(context, attrs)
    }
}