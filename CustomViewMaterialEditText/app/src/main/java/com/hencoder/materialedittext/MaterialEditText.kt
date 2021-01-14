package com.hencoder.materialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 23.dp
private val EXTRA_VERTICAL_OFFSET = 16.dp

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false

    var floatingLabelFraction = 0f // 動畫完成比例
        set(value) {
            field = value
            invalidate() // 在 setter 加入 invalidate() 重繪
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }

    // 配置屬性讓外部可以當作 Lib 使用
    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        }


    init {
        paint.textSize = TEXT_SIZE

        // attrs
        for (index in 0 until attrs.attributeCount) {
            println(
                "Attrs Key: ${attrs.getAttributeName(index)}, Value: ${attrs.getAttributeValue(index)}"
            )
        }

        // 與 attrs.xml 中的屬性連動
        val typeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MaterialEditText // 是 Int 類型的數組
        )

        useFloatingLabel = typeArray.getBoolean(
            R.styleable.MaterialEditText_useFloatingLabel,
            true
        )

        typeArray.recycle() // 回收
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (floatingLabelShown && text.isNullOrEmpty()) {
            floatingLabelShown = false
            animator.reverse()
        } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
            floatingLabelShown = true
            animator.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.alpha = (floatingLabelFraction * 0xff).toInt()

        val currentVerticalValue =
            VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)

        /*
         * 每當文字刷新時，UI 就會被呼叫，若是靜態，則沒問題，若為啟動動畫，則動畫將會一直被重啟。
         */
//        if (!text.isNullOrEmpty()) {...}
        canvas.drawText(
            hint.toString(), // 將 Hint 上移
            HORIZONTAL_OFFSET,
            currentVerticalValue,
            paint
        )
    }
}