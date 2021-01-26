package com.hencoder.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import kotlin.math.abs

class TouchLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 自定義 View，若為非滑動空間，則必須覆寫，並設置為 false；基本上該操作為常規操作。
    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    /*
     * TouchEvent 事件的傳遞：
     *
     * 假設：消費與攔截都尚未發生。
     * 1. 父 View 對每個事件調用 onInterceptTouchEvent() 判斷是否攔截。
     * 2. 若父 View 不攔截，傳給子 View 的 onTouchEvent()。
     * 3. 直到所有子 View 的 onTouchEvent() 都不要，在丟回給父 View 的 onTouchEvent()。
     * 4. 若後續還有父 View 就持續按照此循環。
     *
     * 假設：假設攔截發生。
     * 1. 當父 View 的 onInterceptTouchEvent() 針對某事件攔截，例如用戶滑動。
     * 2. 此時，子 View 應處於預按下狀態。
     * 3. 父 View 會將子 View 的事件所有權奪回，並給調用自己的 onTouchEvent() 來處理滑動事件。
     * 4. 而該事件組就會被父 View 的 onTouchEvent() 消費。
     *
     * 總結：根據自己的畫面邏輯判斷是否要攔截事件，例如符合滑動條件時；但若覆寫 onInterceptTouchEvent()，
     * 就須同時覆寫 onTouchEvent()，因為在 onInterceptTouchEvent() 攔截後就會調用自己的 onTouchEvent()。
     *
     * 備註：事實上，關於真正分派事件的方法是 dispatchTouchEvent()，是屬於 View 的方法；而在 ViewGroup 中，
     * dispatchTouchEvent() 被覆寫，因此會先去調用 onInterceptTouchEvent()，若該值返回 true，
     * 就會調用自己 onTouchEvent()，事實上，該方法是繼承自父類，所以真正調用的是 super.onTouchEvent(event)。
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        /*
         * 在覆寫此方法是，預設應該為 false，僅在需要時返回 true，並將所需要數值預先保存。
         */
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        /*
         * 要注意，傳入的事件僅為當前最新事件，而之前的事件通常不會進到此方法，以滑動為例，當此方法被呼叫時，
         * 也就代表著其已經滑動超過閾值距離，因此，在此方法能取得僅是當前也就是超過閾值的座標，而無法獲取初始位置，
         * 因此，在攔截之前，我們必須先將需要的數值保存，而這些數值的獲取應該在 onInterceptTouchEvent()。
         */
        return super.onTouchEvent(event)
    }
}