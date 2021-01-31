package com.example.app.sample

import com.example.app.entity.User

class Destructuring {
}

data class Response(var code: Int, var message: String, var body: User)

fun execute(): Response {
    println("正在請求網絡...")
    println("網絡請求成功!")

    val code = 200
    val message = "OK"
    val user = User()
    return Response(code, message, user)
}

fun main() {
    /*
     * 傳統作法（Java 中僅能如此）：分次取出
     */
    val response = execute()
//    val body = response.body
//    val code = response.code
//    val message = response.message

    /*
     * 解構作法：一次全部對應取出
     */
    val (code, message, body) = execute()
}