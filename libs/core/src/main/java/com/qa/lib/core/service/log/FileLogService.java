package com.qa.lib.core.service.log;

import com.google.inject.Inject;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public final class FileLogService extends BaseLogService {

    private final Path filePath;
    private final boolean append;

    @Inject
    public FileLogService() {
        ResourceBundle bundle = ResourceBundle.getBundle("log.log-config");
        filePath = Paths.get(System.getProperty("user.dir"), bundle.getString("core.log.file.path"));
        append = Boolean.parseBoolean(bundle.getString("core.log.file.append"));
        ensureParentDirectoryExists();
    }

    @Override
    protected synchronized void log(String level, String message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.append(LocalDateTime.now().format(FORMATTER))
                .append(" [")
                .append(level)
                .append("] ")
                .append(message)
                .append(System.lineSeparator());

        if (throwable != null) {
            builder.append(stackTraceToString(throwable))
                    .append(System.lineSeparator());
        }

        try {
            if (append) {
                Files.write(
                        filePath,
                        builder.toString().getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } else {
                Files.write(
                        filePath,
                        builder.toString().getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to write log file: " + filePath, ex);
        }
    }

    private void ensureParentDirectoryExists() {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to create log directory", ex);
        }
    }

    private String stackTraceToString(@NonNull Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
