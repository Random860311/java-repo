package com.qa.lib.core.gui.service.async;

import javafx.application.Platform;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Executor;

public final class FxUiExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable command) {
        Platform.runLater(command);
    }
}
