package org.example.threadPool;

import java.util.*;
import java.util.concurrent.*;

public class ThreadPool {
    private static final int threadNum = 5;

    private static final ThreadPool instance = new ThreadPool(threadNum);
    private final WorkerThread[] threads;
    private final Queue<Runnable> taskQueue;

    // 对外提供静态方法获取该对象
    public static ThreadPool getInstance() {
        return instance;
    }

    private ThreadPool(int threadCount) {
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

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        if (tasks == null)
            throw new NullPointerException();
        ArrayList<Future<T>> futures = new ArrayList<>(tasks.size());
        boolean done = false;
        try {
            for (Callable<T> t : tasks) {
                Future<T> f = submit(t);
                futures.add(f);
            }
            for (int i = 0, size = futures.size(); i < size; i++) {
                Future<T> f = futures.get(i);
                if (!f.isDone()) {
                    try {
                        f.get();
                    } catch (CancellationException | ExecutionException ignore) {
                    }
                }
            }
            done = true;
            return futures;
        } finally {
            if (!done)
                for (int i = 0, size = futures.size(); i < size; i++)
                    futures.get(i).cancel(true);
        }
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
