package com.example.lesson

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.core.BaseView
import com.example.core.utils.CacheUtils
import com.example.lesson.entity.Lesson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>, Toolbar.OnMenuItemClickListener {
//    private val lessonPresenter = LessonPresenter(this)
//    override fun getPresenter(): LessonPresenter {
//        return lessonPresenter
//    }

    // 委派
    override val presenter: LessonPresenter by lazy {
        // 只會被加載一次，且僅有在被使用到的時候才會被加載
        LessonPresenter(this)
    }

    class Saver(var token: String) {
        operator fun setValue(thisRef: LessonActivity, property: KProperty<*>, value: String) {
            CacheUtils.save("token", value)
        }

        operator fun getValue(thisRef: LessonActivity, property: KProperty<*>): String {
            return CacheUtils.get("token")!!
        }
    }


    private val lessonAdapter = LessonAdapter()
    private lateinit var refreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)
//        val recyclerView = findViewById<RecyclerView>(R.id.list)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = lessonAdapter
//        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        findViewById<RecyclerView>(R.id.list).run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }

//        refreshLayout = findViewById(R.id.swipe_refresh_layout)
//        refreshLayout.setOnRefreshListener { presenter.fetchData() }
//        refreshLayout.isRefreshing = true

        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).run {
            setOnRefreshListener { presenter.fetchData() }
            isRefreshing = true
        }

        presenter.fetchData()
    }

    internal fun showResult(lessons: List<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        presenter.showPlayback()
        return false
    }
}