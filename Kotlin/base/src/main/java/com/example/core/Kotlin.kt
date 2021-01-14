package com.example.core

import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.core.utils.dp2px

// 以 Unit 對應 Java 中的 void，此外，Unit 可以省略。
fun main(): Unit {
    println("Hello World!")

    // 可讀可寫的變量
    var age: Int = 18

    // 唯獨變量，對應 Java 中的 final 修飾詞。
    val name: String = "Kotlin"

    // 創建對象不需要通過 new，且若滿足類型推斷者，可省略類型，且為強型別。
    var java: Java = Java()

    dp2px(6f)

    CacheUtils.get("get")
    Utils.toast("toast")
}

fun doubleNumber(x: Int): Int {
    return x * 2
}