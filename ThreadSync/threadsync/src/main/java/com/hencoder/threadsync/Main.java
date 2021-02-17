package com.hencoder.threadsync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        runSynchronized1Demo();
        runSynchronized2Demo();
//        runSynchronized3Demo();
//        runReadWriteLockDemo();
    }

    /**
     * 使用 Thread 类来定义工作
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start(); // start0(): 屬於 native 方法，與平台相關。
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() { // Runnable 可以重用
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    static void threadFactory() { // 加上工廠方法
        ThreadFactory factory = new ThreadFactory() {
            /*
             * AtomicInteger 是對 int 的包裝，為其增加原子性與同步性。
             */
            AtomicInteger count = new AtomicInteger(0); // int

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + count.incrementAndGet()); // ++count
//                count.getAndIncrement(); // count ++
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };

        /*
         * Executors.newCachedThreadPool() 會返回 ExecutorService，為 Executor 的子類。
         *
         * 重要方法：
         * - shutdown()：保守的結束，意即若正在進行或排隊中的線程，允許其執行完畢，但不接受新的排隊。
         * - shutdownNow()：立刻結束所有線程，藉由 interrupt() 方法。
         *
         * Executors.newCachedThreadPool() 的實作中會創建 ThreadPoolExecutor()，其為線程池。
         */
        Executor executor = Executors.newCachedThreadPool();

        /*
         * Executors.newSingleThreadExecutor() 與 newCachedThreadPool() 類似，
         * 差異僅在於其創建的線程參數為 1，也就是 ThreadPoolExecutor() 的第二個參數為 1，
         * 而 newCachedThreadPool() 中的 ThreadPoolExecutor() 的第二個參數為 INT_MAX_VALUE。
         */
//        Executor executor = Executors.newSingleThreadExecutor();

        /*
         * Executors.newFixedThreadPool() 會創建固定大小的線程池，但即使沒有任務，該線程仍持續消耗，
         * 多用於大量、批次處理。
         */
//        Executor executor = Executors.newFixedThreadPool(10);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        // 自創線程池
        ExecutorService myExecutor = new ThreadPoolExecutor(5, 100,
                5, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());

        myExecutor.execute(runnable);
    }

    static void callable() {
        /*
         * Callable 可以視為有返回值的 Runnable。
         */
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable); // 用 submit()
        while (true) {
            if (future.isDone()) { // 判斷耗時任務是否完成
                try {
                    String result = future.get(); // 獲取 String
                    System.out.println("result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    static void runReadWriteLockDemo() {
        new ReadWriteLockDemo().runTest();
    }
}
