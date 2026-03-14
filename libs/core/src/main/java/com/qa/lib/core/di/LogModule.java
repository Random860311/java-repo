package com.qa.lib.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.qa.lib.core.AppContext;
import com.qa.lib.core.service.log.AggregateLogService;
import com.qa.lib.core.service.log.ConsoleLogService;
import com.qa.lib.core.service.log.FileLogService;
import com.qa.lib.core.service.log.ILogService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class LogModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConsoleLogService.class).in(Singleton.class);
        bind(FileLogService.class).in(Singleton.class);
        bind(AggregateLogService.class).in(Singleton.class);

        bind(ILogService.class).to(AggregateLogService.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @NonNull
    List<ILogService> provideChildLogServices(ConsoleLogService consoleLogService, FileLogService fileLogService) {
        List<ILogService> result = new ArrayList<>();
        result.add(consoleLogService);

        if (AppContext.isProdEnv()) {
            result.add(fileLogService);
        }

        return result;
    }
}
