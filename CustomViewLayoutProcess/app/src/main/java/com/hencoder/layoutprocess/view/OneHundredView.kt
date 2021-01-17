package com.hencoder.layoutprocess.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

class OneHundredView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /*
         * 自定義子 View 時，若期望尺寸與父 View 的定義衝突時：
         *
         * - 父 View 的要求：
         *   android:layout_width="match_parent"
         *   android:layout_height="match_parent"
         *
         * - 子 View 的期望：
         *   setMeasuredDimension(100, 100)
         *
         * - 結果：會依照各個 View 的特性決定是否修正。
         *   若父 View 為 ConstraintLayout：填滿畫面。
         *   若父 View 為 LinearLayout：保持長寬 100。
         *
         * - 結論：在程式設計時，應避免父子 View 的衝突，防止不預期的設計被產生。
         */
        setMeasuredDimension(100, 100)
    }

    /*
     * layout()：該方法會接收由父 View 所傳入的邊界值測量值，並開始實際佈局流程，若在此處強行修子 View 的邊界，
     * 則父 View 就無法再強行修正子 View 的邊界。
     *
     * 備註：但不建議如此操作，通常情況下，我們是不會去覆寫 layout()。
     */
//    override fun layout(l: Int, t: Int, r: Int, b: Int) {
//        super.layout(l, t, l + 100, t + 100)
//    }

    /*
     * onLayout()：用於保存其子 View 的邊界值。
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}