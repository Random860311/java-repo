package com.qa.lib.core.util;

public final class TaskExecutionResult<T> {
    public final T result;
    public final Throwable throwable;

    public TaskExecutionResult(T result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }
}
