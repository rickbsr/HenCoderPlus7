package com.example.app.sample

//class View {
//
//    interface OnClickListener {
//        fun onClick(view: View)
//    }
//
//    fun setOnClickListener(listener: OnClickListener) {
//
//    }
//}
//
//fun main() {
//    val view = View()
//    view.setOnClickListener(object : View.OnClickListener {
//        override fun onClick(view: View) {
//            println("被點擊了")
//        }
//    })
//}

// 函數類型
class View {
    fun setOnClickListener(listener: (View) -> Unit) {
    }
}

fun main() {
    val view = View()
    view.setOnClickListener(::onClick)
}

fun onClick(view: View) {
    println("被點擊了")
}