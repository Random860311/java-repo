package com.qa.lib.core.gui.service.dialog;

import com.google.inject.Inject;
import com.qa.lib.core.gui.dialog.AlertDialog;
import com.qa.lib.core.gui.dialog.LoadingDialog;
import com.qa.lib.core.service.i18n.II18nService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DialogServiceImp implements IDialogService {
    private final II18nService i18nService;

    private final LoadingDialog loadingDialog;
    private final AlertDialog alertDialog;

    @Inject
    public DialogServiceImp(II18nService i18nService, LoadingDialog loadingDialog, AlertDialog alertDialog) {
        this.i18nService = i18nService;

        this.loadingDialog = loadingDialog;
        this.alertDialog = alertDialog;
    }

    @Override
    public void showInfiniteDialog() {
        loadingDialog.show(i18nService.getString("core.gui.dialog.progress.loading"));
    }

    @Override
    public void showInfiniteDialog(String message) {
        loadingDialog.show(message);
    }

    @Override
    public void hideInfiniteDialog() {
        loadingDialog.close();
    }

    @Override
    public CompletableFuture<Void> showInfoAsync(String message) {
        return alertDialog.showInfoAsync(message);
    }

    @Override
    public CompletableFuture<Void> showErrorAsync(String message, Throwable throwable) {
        return alertDialog.showErrorAsync(message, throwable);
    }

    @Override
    public CompletableFuture<Boolean> showConfirmationAsync(String message) {
        return alertDialog.showConfirmationAsync(message);
    }


}
