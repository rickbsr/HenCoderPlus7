package com.hencoder.touch.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class TouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        val TAG: String = TouchView::class.java.simpleName
    }

    /*
     * 藉由 event 事件來判斷觸摸的情況。
     *
     * - onTouchEvent 是層層向下傳遞（非無條件穿透），由離使用者最近的 View 開始觸發。
     * - 所有的觸摸事件都不是獨立的，而是一個個序列，且是有關連的。
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        /*
         * getActionMasked() vs getAction()：
         *
         * 差異在於多點觸控的判斷，getActionMasked() 包含了 ACTION_POINTER_DOWN / ACTION_POINTER_UP，
         * 它還包含著觸摸的手指數量信息。
         *
         * 單點觸控：
         * - MotionEvent.ACTION_UP
         * - MotionEvent.ACTION_DOWN
         * - MotionEvent.ACTION_CANCEL
         * - MotionEvent.ACTION_MOVE
         *
         * 多點觸控（包含 POINTER 資訊）：
         * - MotionEvent.ACTION_POINTER_DOWN
         * - MotionEvent.ACTION_POINTER_UP
         *
         * 總結：
         * - getAction()：屬於單點觸控的信息，它不區分單點、多點，或著說，它會將單點、多點的訊息融合。
         * - getActionMasked()：屬於完整的信息，區分單點、多點。
         * - Google 也建議使用 getActionMasked() 來取代 getAction()。
         *
         * 備註：剛開始支援多點觸控是以 ACTION_POINTER_1_DOWN、ACTION_POINTER_2_DOWN…等；但這並不足夠，
         * 因此，舊有的方式就被棄用，而改用新的標籤。
         *
         * Case 1：
         * - event.action == MotionEvent.ACTION_POINTER_UP
         * Result 1：
         * - 總是 false，因為 ACTION_POINTER_UP 更詳細的訊息。
         */
        if (event.action == MotionEvent.ACTION_POINTER_UP)
            Log.d(TAG, "onTouchEvent: event.action == MotionEvent.ACTION_POINTER_UP")

        /*
         * Case 2：
         * - event.action == MotionEvent.ACTION_UP
         * - Result 2：在符合的情況下，會為 true，事實上，getAction() 所取得的信息是合併後的信息。
         */
        if (event.action == MotionEvent.ACTION_UP)
            Log.d(TAG, "onTouchEvent: event.action == MotionEvent.ACTION_UP")

        if (event.actionMasked == MotionEvent.ACTION_POINTER_1_DOWN) {

            /*
             * performClick()：
             *
             * 是 View 的方法，會觸發點擊。
             * - li.mOnClickListener.onClick(this);
             */
            performClick()
        }
        /*
         * 返回值：是「所有權」標誌，僅跟 DOWN 事件相關。
         *
         * - true：表示該事件與其相關後續事件都由當前 View 處理，而不再向下傳遞。
         * - false：略過不處理，讓事件向下傳遞。
         *
         * 備註：DOWN 為序列的起頭，因此要就整個序列要，否怎就該序列整個不要。
         */
        return true
    }
}