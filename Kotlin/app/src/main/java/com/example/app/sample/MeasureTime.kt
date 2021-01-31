package com.example.app.sample

inline fun measureTime(action: () -> Unit) {
    println(">>>> ")
    val start = System.currentTimeMillis()

    action()

    val end = System.currentTimeMillis()
    println("<<<< [${end - start}]")
}

fun main() {
    measureTime { println("Hello Kotlin") }
}