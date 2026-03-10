package com.qa.lib.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.qa.lib.core.qualifiers.BackgroundThread;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class AsyncModule extends AbstractModule {
    @Override
    protected void configure() {}

    @Provides
    @Singleton
    @BackgroundThread
    ExecutorService provideBackgroundExecutorService() {
        ThreadFactory tf = new ThreadFactory() {
            private final AtomicInteger n = new AtomicInteger(1);

            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(r, "bg-" + n.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        };

        return Executors.newFixedThreadPool(4, tf);
    }

    // Bind as Executor too, so consumers can inject @Background Executor (not only ExecutorService)
    @Provides
    @Singleton
    @BackgroundThread
    Executor provideBackgroundExecutor(@BackgroundThread ExecutorService es) {
        return es;
    }
}
