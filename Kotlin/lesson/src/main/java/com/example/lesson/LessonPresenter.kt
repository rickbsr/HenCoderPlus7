package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient.get
import com.example.core.utils.Utils.toast
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.util.*


class LessonPresenter {

    companion object {
        // 藉由 const 表示編譯器常量
        const val LESSON_PATH = "lessons"
    }

    private var activity: LessonActivity? = null

    constructor(activity: LessonActivity?) {
        this.activity = activity
    }

    private var lessons: List<Lesson> = ArrayList()

    private val type = object : TypeToken<List<Lesson?>?>() {}.type

    fun fetchData() {
        get(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(lessons: List<Lesson>) {
                /*
                 * 引用外部類成員：
                 *
                 * 在 Java 中，類名.this.成員
                 * 在 Kotlin 中，this@類名.成員
                 */
                this@LessonPresenter.lessons = lessons

                activity!!.runOnUiThread { activity!!.showResult(lessons) }
            }

            override fun onFailure(message: String?) {
                activity!!.runOnUiThread { toast(message) }
            }
        })
    }

    fun showPlayback() {
        /*
         * 在 Kotlin 中，List 和 Map 是不支持修改內容的。
         */
//        val playbackLessons: MutableList<Lesson> = ArrayList()
//        for (lesson in lessons) {
//            if (lesson.state === Lesson.State.PLAYBACK) {
//                playbackLessons.add(lesson)
//            }
//        }

//        lessons.forEach {
//            if (it.state === Lesson.State.PLAYBACK) playbackLessons.add(it)
//        }

//        val filter = lessons.filter { it.state === Lesson.State.PLAYBACK }

        activity!!.showResult(lessons.filter {
            it.state === Lesson.State.PLAYBACK
        })
    }
}