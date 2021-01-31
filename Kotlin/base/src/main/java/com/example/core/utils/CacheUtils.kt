package com.example.core.utils

import android.content.Context
import com.example.core.BaseApplication
import com.example.core.R

/*
 * 以 object 創建類別，其中所有的成員、屬性，都屬於單例的。
 *
 * - 在 Java，用類名.INSTANCE.方法名
 * - 在 Kotlin，用類名.方法名
 */
object CacheUtils {
    private val context = BaseApplication.currentApplication

    private val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

//    fun save(key: String?, value: String?) {
//        SP.edit().putString(key, value).apply()
//    }

    fun save(key: String?, value: String?) = SP.edit().putString(key, value).apply()

//    fun get(key: String?): String? {
//        return SP.getString(key, null)
//    }

    fun get(key: String?) = SP.getString(key, null)
}