package com.hencoder.threadsync;

class SingleMan {
    private static volatile SingleMan sInstance;

    private SingleMan() {
    }

    // 性能差
//    static synchronized SingleMan newInstance() {
//    }

    static SingleMan newInstance() {
        if (sInstance == null) {
            synchronized (SingleMan.class) { // 是 null 才執行 synchronized 操作
                if (sInstance == null) { // 排除排隊創建
                    sInstance = new SingleMan();
                }
            }
        }
        return sInstance;
    }
}
