package com.example.core

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    /*
     * 伴生對象，單例。
     *
     * - 在 Kotlin，類名.方法名
     * - 在 Java，類名.Companion.方法名
     */
    companion object {

        @JvmStatic
        @get:JvmName("currentApplication")
        lateinit var currentApplication: Context
            private set

        /*
         * 藉由 @JvmStatic 就可以將欄位或方法聲明成 Java 中的 static 類。
         */
//        @JvmStatic
//        fun currentApplication(): Context {
//            return currentApplication
//        }
    }

    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }
}