package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.LessonAdapter.LessonViewHolder
import com.example.lesson.entity.Lesson
import java.util.*

class LessonAdapter : RecyclerView.Adapter<LessonViewHolder>() {
    private var list: List<Lesson> = ArrayList()
    internal fun updateAndNotify(list: List<Lesson>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        // operator
        holder.onBind(list[position])
    }

    /*
     * 內部類別：
     *
     * 在 Java 中，預設為嵌套內部類，靜態內部類須加上 static。
     * 在 Kotlin 中，預設為靜態內部類，嵌套內部類需加上關鍵字 inner。
     */
    class LessonViewHolder internal constructor(itemView: View) : BaseViewHolder(itemView) {
        internal fun onBind(lesson: Lesson) {
//            var date = lesson.date ?: "日期待定" // 給定默認值
//            if (date == null) {
//                date = "日期待定"
//            }

            setText(R.id.tv_date, lesson.date ?: "日期待定")
            setText(R.id.tv_content, lesson.content)

//            val state = lesson.state

            // let 適合用於空判斷，也以換成 also，只是返回值的差異
//            lesson.state?.let {
            lesson.state?.also {
                setText(R.id.tv_state, it.stateName())
                val colorRes = when (it) {
                    Lesson.State.PLAYBACK -> R.color.playback
                    Lesson.State.LIVE -> R.color.live
                    Lesson.State.WAIT -> R.color.wait
                }

                val backgroundColor = itemView.context.getColor(colorRes)
                getView<View>(R.id.tv_state)!!.setBackgroundColor(backgroundColor)
            }
            /*
            if (state != null) {
                setText(R.id.tv_state, state.stateName())
//                var colorRes = R.color.playback
//                colorRes = when (state) {
//                    Lesson.State.PLAYBACK -> {
//
//                        // 即使在 {} 中也是需要 break 的。
//                        R.color.playback
//                    }
//                    Lesson.State.LIVE -> R.color.live
//                    Lesson.State.WAIT -> R.color.wait
//                }

                val colorRes = when (state) {
                    Lesson.State.PLAYBACK -> R.color.playback
                    Lesson.State.LIVE -> R.color.live
                    Lesson.State.WAIT -> R.color.wait
                }

                val backgroundColor = itemView.context.getColor(colorRes)
                getView<View>(R.id.tv_state)!!.setBackgroundColor(backgroundColor)
            }
             */
        }

        companion object {
            fun onCreate(parent: ViewGroup): LessonViewHolder {
                return LessonViewHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_lesson, parent, false))
            }
        }
    }
}