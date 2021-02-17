package com.hencoder.threadsync;

public class Synchronized3Demo implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;

    /*
     * synchronized 會為方法提供 monitor，其關注的是資源，而非方法，所以下述三個方法全部共享同個 monitor。
     */
//    private synchronized void count(int newValue) {
//        x = newValue;
//        y = newValue;
//    }
//
//    private synchronized void minus(int delta) {
//        x -= delta;
//        y -= delta;
//    }
//
//    private synchronized void setName(String newName) {
//        name = newName;
//    }


    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();

    private void count(int newValue) {
        synchronized (monitor1) {
            x = newValue;   // monitor
            y = newValue;
        }
    }

    private void minus(int delta) {
        synchronized (monitor1) {
            x -= delta;
            y -= delta;
        }
    }

    private synchronized void setName(String newName) {
        synchronized (monitor2) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }
}