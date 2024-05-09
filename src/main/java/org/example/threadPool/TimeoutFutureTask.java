package org.example.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TimeoutFutureTask<T> extends FutureTask<T> {
    private final long timeoutMillis;

    public TimeoutFutureTask(Callable<T> callable, long timeoutMillis) {
        super(callable);
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}
