package org.example.threadPool;

import java.util.*;
import java.util.concurrent.*;

public class ThreadPool {
    private static final int threadNum = 5;

    private static final ThreadPool instance = new ThreadPool(threadNum);
    private final WorkerThread[] threads;
    private final Queue<FutureTask> taskQueue;

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

    /**
     *
     * @param task  Callable<T>
     * @param timeout 单位为毫秒
     * @return Future<T>
     * @param <T>
     */
    public <T> Future<T> submit(Callable<T> task, int timeout) {
//        FutureTask<T> futureTask = new FutureTask<>(task);
        TimeoutFutureTask<T> futureTask = new TimeoutFutureTask<>(task, timeout);
        synchronized (taskQueue) {
            taskQueue.add(futureTask);
            taskQueue.notify();
        }
        return futureTask;
    }


    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, int timeout) {
        if (tasks == null)
            throw new NullPointerException();
        ArrayList<Future<T>> futures = new ArrayList<>(tasks.size());
        boolean done = false;
        try {
            for (Callable<T> t : tasks) {
                futures.add(submit(t,timeout));
            }
            for (int i = 0, size = futures.size(); i < size; i++) {
                Future<T> f = futures.get(i);
                if (!f.isDone()) {
                    try {
                        f.get();
                    } catch (CancellationException e) {
                        taskQueue.clear();
                        done = true;
                        return futures;
                    } catch (ExecutionException ignore){
                        taskQueue.clear();
                        f.cancel(true);
                        done = true;
                        return futures;
                    }catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            done = true;
            return futures;
        } finally {
            if (!done)
                for (Future<T> future : futures) future.cancel(true);
        }
    }

    public <T> boolean invokeAll(Collection<? extends Callable<T>> tasks, int timeout, List<String> outputList) {
        if (tasks == null)
            throw new NullPointerException();
        ArrayList<Future<T>> futures = new ArrayList<>(tasks.size());
        boolean done = false;
        try {
            for (Callable<T> t : tasks) {
                futures.add(submit(t,timeout));
            }
            for (int i = 0, size = futures.size(); i < size; i++) {
                Future<T> f = futures.get(i);

                try {
                    String re = (String) f.get();
                    if (!re.equals(outputList.get(i))){
                        return false;
                    }
                } catch (CancellationException e) {
                    return false;
                } catch (ExecutionException ignore){
                    return false;
                }catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            done = true;
            return true;
        } finally {
            if (!done)
                for (Future<T> future : futures) future.cancel(true);
        }
    }
//    // Define Pair class
//    static class ExecutionResult {
//        @Getter
//        private final ExecutionStatus status;
//        private final List<Future> value;
//
//        public ExecutionResult() {
//            status = ExecutionStatus.WAITING;
//            value = new ArrayList<>();
//        }
//
//        public ExecutionResult(ExecutionStatus status, List<Future> value) {
//            this.status = status;
//            this.value = value;
//        }
//
//        public List<Future> getValue() {
//            return value;
//        }
//
//        public boolean isSuccess() {
//            return status.equals(ExecutionStatus.SUCCESSFUL);
//        }
//
//        public void addTaskFuture(Future<?> future) {
//            value.add(future);
//        }
//    }

    public void shutdown() {
        for (WorkerThread thread : threads) {
            thread.shutdown();
        }
    }

}
