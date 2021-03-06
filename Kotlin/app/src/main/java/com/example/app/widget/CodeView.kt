package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.app.R
import com.example.core.utils.Utils
import com.example.core.utils.dp2px
import java.util.*

class CodeView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) :
        AppCompatTextView(context, attr) {

//    constructor(context: Context) : this(context, null)

    // apply 可以放置初始化代碼
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = getContext().getColor(R.color.colorAccent)
        strokeWidth = 6f.dp2px()
    }

    // let 也有同樣效果，但語法上並不合適
//    private val paint = Paint().let {
//        it.isAntiAlias = true
//        it.style = Paint.Style.STROKE
//        it.color = getContext().getColor(R.color.colorAccent)
//        it.strokeWidth = 6f.dp2px()
//        return@let
//    }

    // 若為基本數據類型，則不建議用一般數組存放，因為數組存放的是包裝後類型，若為基本數據類型則需要封裝、拆裝。
    private val codeList = arrayOf(
            "kotlin",
            "android",
            "java",
            "http",
            "https",
            "okhttp",
            "retrofit",
            "tcp/ip"
    )

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        gravity = Gravity.CENTER
        setBackgroundColor(getContext().getColor(R.color.colorPrimary))
        setTextColor(Color.WHITE)

//        paint.isAntiAlias = true
//        paint.style = Paint.Style.STROKE
//        paint.color = getContext().getColor(R.color.colorAccent)
//        paint.strokeWidth = 6f.dp2px()

        updateCode()
    }

    fun updateCode() {
        val random = Random().nextInt(codeList.size)
        val code = codeList[random]
        text = code
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        super.onDraw(canvas)
    }
}
