package com.qa.lib.core.gui.dialog;

import com.google.inject.Inject;
import com.qa.lib.core.AppContext;
import com.qa.lib.core.gui.controller.dialog.LoadingDialogController;
import com.qa.lib.core.gui.qualifiers.UiThread;
import com.qa.lib.core.service.i18n.II18nService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class LoadingDialog {
    private final Stage stage;
    private final LoadingDialogController controller;
    private final Executor uiExecutor;

    @Inject
    public LoadingDialog(II18nService i18nService, @UiThread Executor uiExecutor) {
        this.uiExecutor = uiExecutor;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/loading-dialog.fxml"),
                    i18nService.getBundle()
            );
            loader.setControllerFactory(AppContext.getInjector()::getInstance);

            Parent root = loader.load();

            controller = loader.getController();

            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load loading dialog", e);
        }
    }

    public void show(String message) {
        CompletableFuture.runAsync(() -> {
            controller.setMessage(message);
            if (!stage.isShowing()) {
                stage.show();
            } else {
                stage.toFront();
            }
        }, uiExecutor);

    }

    public void close() {
        CompletableFuture.runAsync(stage::close, uiExecutor);

    }
}
