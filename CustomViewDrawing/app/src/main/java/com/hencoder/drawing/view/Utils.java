package com.hencoder.drawing.view;

import android.content.res.Resources;
import android.util.TypedValue;

class Utils {
    public static float dp2px(float value) {
        return
                // 可以藉由 TypedValue.applyDimension() 將 dp 轉成 px 值。
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        value, // 輸入 dp 的值

                        /*
                         * 關於 Resources.getSystem().getDisplayMetrics()
                         *
                         * 若要取得螢幕像素密度的資源訊息，我們就需要上下文。
                         * 因此，過去常見的方式是傳「View」或「Context」，就是將「Activity」本身傳入：
                         * - view.getResources().getDisplayMetrics()
                         * - context.getResources().getDisplayMetrics()
                         *
                         * 但實際上，螢幕像素密度與目標「Activity」無關，是屬於系統上下文，因此以之替代：
                         * - Resources.getSystem().getDisplayMetrics()
                         *
                         * 雖然後者更少被使用，但後者的確是更好的用法。
                         */
                        Resources.getSystem().getDisplayMetrics()
                );
    }
}
