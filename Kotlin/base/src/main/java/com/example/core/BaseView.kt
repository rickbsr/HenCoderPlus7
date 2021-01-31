package com.example.core

interface BaseView<T> {
//    fun getPresenter(): T

    // 抽象屬性
    val presenter: T
}