package com.hencoder.threadsync;

public class Synchronized2Demo implements TestDemo {

    private int x = 0;

    /*
     * 此處的 synchronized 使該方法同步，也就是說，該方法具有原子性。
     */
    private synchronized void count() {
        /*
         * int temp = x + 1;
         * x = temp;
         *
         * x++; 會被拆成上述兩行，故非原子操作，其原因為線程切換可以在任意原子操作之間，因此，只要非原子操作，
         * 就有可能因為線程的切換導致線程安全問題。
         *
         * 總結：因為 x++; 非原子操作，因此 volatile 無效。
         */
        x++;
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 1: " + x);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 2: " + x);
            }
        }.start();
    }
}
