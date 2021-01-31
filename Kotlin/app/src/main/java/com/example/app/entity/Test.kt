package com.example.app.entity

import android.util.Log

/*
 * data 修飾會讓 class 自動產生一些方法，例如 copy()
 */
//val user = User("AA", "BB", "CC")
//val userCopy = user.copy()

fun main() {
//    println(user)
//    println(user == userCopy) // 比內容，等於 equals()
//    println(user === userCopy) // 比記憶體位址


//    repeat(100) {
//        println(it)
//    }

//    for (i in 0..99) {
//    }

//    val array = arrayOf(1, 23, 543, 6, 457, 1)

    // infix 可以讓函數調用時省略點跟掛號
//    for (i in 0 until (array.size)) {
//    for (i in 0 until array.size) {
//    }

//    for (i in array.indices) {
//    }

    log("log")
}

/*
 * inline 修飾，當該方法被調用時，其不會是調用，而是在編譯時期將其代碼複製過去；雖然這樣可以減少調用棧，但是，
 * 會增加編譯的工作量，尤其當代碼多次使用時。
 */
inline fun log(text: String) {
    Log.d("TAG", text)
}