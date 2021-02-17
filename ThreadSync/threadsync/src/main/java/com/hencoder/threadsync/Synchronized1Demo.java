package com.hencoder.threadsync;

import java.util.concurrent.atomic.AtomicBoolean;

public class Synchronized1Demo implements TestDemo {

    // volatile 修飾，讓該值確保每次都被讀取。
//    private volatile boolean running = true;

    // 使用 AtomicBoolean 包覆類別，來增加 boolean 原子性、同步性。
    private AtomicBoolean running = new AtomicBoolean(true);

    private void stop() {
//        running = false;
        running.set(false);
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
//                while (running) {
                while (running.get()) {
                }
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }
}
