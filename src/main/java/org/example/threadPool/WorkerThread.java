package org.example.threadPool;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.FutureTask;

class WorkerThread extends Thread {
    private final Queue<FutureTask> taskQueue;
    private volatile boolean running = true;
    private static Timer timer = new Timer(true);

    public WorkerThread(Queue<FutureTask> taskQueue) {
        this.taskQueue = taskQueue;
//        setDaemon(true);
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
//                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                task = taskQueue.poll();//从队列中取出并移除队首的元素
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!task.isDone()){
                        task.cancel(true);
                    }
                }
            },1000);
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
