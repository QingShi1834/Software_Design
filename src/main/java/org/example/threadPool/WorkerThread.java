package org.example.threadPool;

import java.util.Queue;

class WorkerThread extends Thread {
    private final Queue<Runnable> taskQueue;
    private volatile boolean running = true;

    public WorkerThread(Queue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (running) {
            Runnable task;
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait(); // Wait for task
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                task = taskQueue.poll();
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
