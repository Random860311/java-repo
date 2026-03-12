package com.qa.lib.core.service.log;

import java.time.LocalDateTime;

public final class ConsoleLogService extends BaseLogService {
    @Override
    protected void log(String level, String message, Throwable throwable) {
        String formatted = String.format(
                "%s [%s] %s",
                LocalDateTime.now().format(FORMATTER),
                level,
                message
        );

        if ("ERROR".equals(level)) {
            System.err.println(formatted);
        } else {
            System.out.println(formatted);
        }

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }
}
