package com.hencoder.animation

import android.animation.*
import android.graphics.PointF
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.hencoder.animation.view.ProvinceEvaluator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * ViewPropertyAnimator
         *
         * 最基本、簡單的動畫；但限制極大，僅有幾個方法可以調用。
         */
//        view.animate()
//            .translationX(200.dp) // setTranslationX(10) setTranslationX(20) setTranslationX(40)...
//            .translationY(100.dp)
//            .alpha(0.5f)
//            .scaleX(2f)
//            .scaleY(2f)
//            .rotation(90f)
//            .startDelay = 1000 // 延遲開啟

        /*
         * ObjectAnimator
         *
         * 基於性能考慮，當屬性改變時，畫面並不會自動刷新，因此要在 CustomView 的 Property setter 中調用 invalidate() 來重繪，但一次調用僅能針對一個屬性。
         */
//        val animator = ObjectAnimator.ofFloat(
//            view, // Custom View
//            "radius", // 屬性值
//            150.dp
//        )
//        animator.startDelay = 1000
//        animator.start()

        /*
         * ObjectAnimatorSet
         */
//        val bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
//        bottomFlipAnimator.startDelay = 1000
//        bottomFlipAnimator.duration = 1000
//
//        val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
//        flipRotationAnimator.startDelay = 200
//        flipRotationAnimator.duration = 1000
//
//        val topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -60f)
//        topFlipAnimator.startDelay = 200
//        topFlipAnimator.duration = 1000
//
//        // 將上述個動畫合併到一起
//        val animatorSet = AnimatorSet()
//        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator)
//        animatorSet.start()

        /*
         * PropertyValuesHolder
         *
         * 將同對象的動畫作的更細緻，可以搭配 Keyframe
         */
//        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
//        val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
//        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -60f)
//
//        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(
//            view,
//            bottomFlipHolder,
//            flipRotationHolder,
//            topFlipHolder
//        )
//        holderAnimator.startDelay = 1000
//        holderAnimator.duration = 2000
//        holderAnimator.start()

        // keyframe1
//        val length = 200.dp
//        val keyframe1 = Keyframe.ofFloat( // 時間完成度對於動畫完成度的映射
//            0f, // 完成度比率
//            0f // 距離
//        )
//        val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length)
//        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
//        val keyframe4 = Keyframe.ofFloat(1f, 1f * length)
//        // 藉由 PropertyValuesHolder 整合
//        val keyframeHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4
//        )
//        val animator = ObjectAnimator.ofPropertyValuesHolder(view, keyframeHolder)
//        animator.startDelay = 1000
//        animator.duration = 2000
//        animator.start()

        /*
         * interpolator，可以客製化，但常用是下面四種：
         *
         * - AccelerateDecelerateInterpolator()：不涉及元件的出場入場。
         * - AccelerateInterpolator()：加速動畫，常用於出場，像是飛出螢幕。
         * - DecelerateInterpolator()：減速動畫，常用於入場。
         * - LinearInterpolator()：等速動畫。
         */
//        val animator = ObjectAnimator.ofFloat()
//        animator.interpolator = AccelerateDecelerateInterpolator()

        /*
         * 自定義 TypeEvaluator
         */
//        val animator =
//            ObjectAnimator.ofObject(view, "point", PointFEvaluator(), PointF(100.dp, 200.dp))
//        animator.startDelay = 1000
//        animator.duration = 2000
//        animator.start()

        /*
         * 自定義 TypeEvaluator：字串動畫
         */
        val animator =
            ObjectAnimator.ofObject(view, "province", ProvinceEvaluator(), "澳门特别行政区")
        animator.startDelay = 1000
        animator.duration = 10000

        animator.start()

        /*
         * 離屏緩衝：
         *
         * 優點：對於 Android 動畫自帶屬性，離屏緩衝能在不重繪製的情況下，使得 UI 改變，因此效能更好。
         * 缺點：其效果不包含自定義屬性，此外，開啟離屏緩衝本身就是一種額外消耗。
         */
//        view.animate()
//            .translationY(200.dp)
//            .withLayer() // 在動畫過程中開啟硬件等級的離屏緩衝
    }

    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            // X：(起始值 + 差值) * 百分比
            val startX = startValue.x
            val endX = endValue.x
            val currentX = startX + (endX - startX) * fraction

            // Y：(起始值 + 差值) * 百分比
            val startY = startValue.y
            val endY = endValue.y
            val currentY = startY + (endY - startY) * fraction

            return PointF(currentX, currentY)
        }
    }
}