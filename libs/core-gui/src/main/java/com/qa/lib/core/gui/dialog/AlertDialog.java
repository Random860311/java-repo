package com.qa.lib.core.gui.dialog;

import com.google.inject.Inject;
import com.qa.lib.core.gui.qualifiers.UiThread;
import com.qa.lib.core.service.i18n.II18nService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class AlertDialog extends BaseDialog {
    @Inject
    public AlertDialog(@UiThread Executor uiExecutor, II18nService i18nService) {
        super(uiExecutor, i18nService);
    }

    public @NonNull CompletableFuture<Void> showInfoAsync(String message) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        show(() -> {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(i18nService.getString("core.gui.info"));
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();

                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    public @NonNull CompletableFuture<Void> showErrorAsync(String message, Throwable throwable) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        show(() -> {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(i18nService.getString("core.gui.error"));
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();

                if (throwable != null) {
                    future.completeExceptionally(throwable);
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    public @NonNull CompletableFuture<Boolean> showConfirmationAsync(String message) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        show(() -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(i18nService.getString("core.gui.confirm"));
                alert.setHeaderText(null);
                alert.setContentText(message);

                Optional<ButtonType> result = alert.showAndWait();
                future.complete(result.isPresent() && result.get() == ButtonType.OK);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    private void show(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
