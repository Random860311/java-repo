package com.qa.lib.core.service.log;

import java.time.format.DateTimeFormatter;

public abstract class BaseLogService implements  ILogService {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void debug(String message) {
        log("DEBUG", message, null);
    }

    @Override
    public void info(String message) {
        log("INFO", message, null);
    }

    @Override
    public void warn(String message) {
        log("WARN", message, null);
    }

    @Override
    public void error(String message) {
        log("ERROR", message, null);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log("ERROR", message, throwable);
    }

    protected abstract void log(String level, String message, Throwable throwable);
}
