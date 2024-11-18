package fr.joupi.api.utils.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class MultiThreading {

    private final AtomicInteger counter = new AtomicInteger(0);

    public final ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadBuilder(counter).builder().setNameFormat("fixed-thread-%d").build());
    public final ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(3, new ThreadBuilder(counter).builder().setNameFormat("scheduled-thread-%s").build());

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return scheduledPool.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, TimeUnit unit) {
        return scheduledPool.schedule(runnable, initialDelay, unit);
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, pool);
    }

    public int getTotal() {
        return counter.get();
    }

    private class ThreadBuilder {

        public ThreadBuilder(AtomicInteger counter) {
            counter.incrementAndGet();
        }

        public ThreadFactoryBuilder builder() {
            return new ThreadFactoryBuilder();
        }

    }

}