package org.example.threadPool;

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class WorkerThread extends Thread {
    private final Queue<FutureTask> taskQueue;
    private volatile boolean running = true;
    private static ScheduledExecutorService Timer = Executors.newScheduledThreadPool(1);
    public WorkerThread(Queue<FutureTask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (running) {
            FutureTask task;
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait(); // Wait for task
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        return;
                    }
                }
                task = taskQueue.poll();//从队列中取出并移除队首的元素
            }
            if (task instanceof TimeoutFutureTask) {
                long timeout = ((TimeoutFutureTask<?>) task).getTimeoutMillis();
                Timer.schedule(() -> {
                    if (!task.isDone()) {
                        task.cancel(true);
                    }
                }, timeout, TimeUnit.MILLISECONDS);
            }
            try {
                task.run(); // Execute task
            } catch (RuntimeException e) {
                System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt(); // Interrupt thread if it's waiting
    }
}
