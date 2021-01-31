package com.example.app.entity;

import com.example.app.sample.View;
import com.example.core.BaseApplication;
import com.example.core.utils.Utils;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class A {

    public static void main(String[] args) {
        BaseApplication.currentApplication();

        Utils.INSTANCE.toast("a");

        // 在 Java 調用函數類型
        new View().setOnClickListener(new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                return null;
            }
        });
    }
}
