package com.example.core;

import com.example.core.utils.KotlinUtils;
import com.example.core.utils.Utils;
//import com.example.core.utils.UtilsKt;

public class Java {

    public static void main(String[] args) {
        int age = 18;
        final String name = "Java";
        Java java = new Java();

        // 以文件名稱調用 Kotlin 的頂層函數
//        UtilsKt.dp2px(12f);

        // 藉由 @file:JvmName("KotlinUtils") 取代文件名
        KotlinUtils.dp2px(12f);


        // 調用 object 函數，要用 INSTANCE
        Utils.INSTANCE.toast("Java");

        // 調用 companion 函數，要用 Companion
        BaseApplication.Companion.currentApplication();

        BaseApplication.currentApplication();
    }
}
