package org.example.threadPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ThreadPool {
    private final WorkerThread[] threads;
    private final Queue<Runnable> taskQueue;

    public ThreadPool(int threadCount) {
        threads = new WorkerThread[threadCount];
        taskQueue = new LinkedList<>();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new WorkerThread(taskQueue);
            threads[i].start();
        }
    }

    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> futureTask = new FutureTask<>(task);
        synchronized (taskQueue) {
            taskQueue.add(futureTask);
            taskQueue.notify();
        }
        return futureTask;
    }

    public void shutdown() {
        for (WorkerThread thread : threads) {
            thread.shutdown();
        }
    }

//    public static void main(String[] args) {
//        ThreadPool threadPool = new ThreadPool(5);
//
//        // Submit tasks
//        for (int i = 0; i < 10; i++) {
//            final int taskId = i;
//            threadPool.submit(() -> {
//                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
//            });
//        }
//
//        // Shutdown pool after tasks are completed
//        threadPool.shutdown();
//    }
}
