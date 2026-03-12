package com.qa.lib.core.service.log;

public interface ILogService {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable throwable);
}
