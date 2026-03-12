package com.qa.lib.core.service.log;

import com.google.inject.Inject;

import java.util.List;

public final class AggregateLogService implements ILogService {
    private final List<ILogService> logServices;

    @Inject
    public AggregateLogService(List<ILogService> logServices) {
        this.logServices = logServices;
    }

    @Override
    public void debug(String message) {
        for (ILogService logService : logServices) {
            logService.debug(message);
        }
    }

    @Override
    public void info(String message) {
        for (ILogService logService : logServices) {
            logService.info(message);
        }
    }

    @Override
    public void warn(String message) {
        for (ILogService logService : logServices) {
            logService.warn(message);
        }
    }

    @Override
    public void error(String message) {
        for (ILogService logService : logServices) {
            logService.error(message);
        }
    }

    @Override
    public void error(String message, Throwable throwable) {
        for (ILogService logService : logServices) {
            logService.error(message, throwable);
        }
    }
}
