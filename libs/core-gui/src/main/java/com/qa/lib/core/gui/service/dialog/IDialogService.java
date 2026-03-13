package com.qa.lib.core.gui.service.dialog;

import java.util.concurrent.CompletableFuture;

public interface IDialogService {
    void showInfiniteDialog();
    void showInfiniteDialog(String message);
    void hideInfiniteDialog();

    CompletableFuture<Void> showInfoAsync(String message);
    default CompletableFuture<Void> showErrorAsync(String message) { return showErrorAsync(message,null); }
    CompletableFuture<Void> showErrorAsync(String message, Throwable throwable);
    CompletableFuture<Boolean> showConfirmationAsync(String message);
}
