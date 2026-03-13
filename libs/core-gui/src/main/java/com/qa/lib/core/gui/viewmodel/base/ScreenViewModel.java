package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class ScreenViewModel extends BaseViewModel implements IParameterReceiver {
    protected final INavigationService navigationService;
    protected final IDialogService dialogService;

    protected Object navigationParameter = null;

    @Inject
    public ScreenViewModel(INavigationService navigationService, IDialogService dialogService) {
        super();
        this.navigationService = navigationService;
        this.dialogService = dialogService;
    }

    @Override
    public void setNavigationParameter(Object parameter) {
        navigationParameter = parameter;
    }

    public void onInitialize() {
    }

    protected CompletableFuture<Void> executeTask(Runnable task, String loadingMessage, String successMessage, String failMessage) {
        dialogService.showInfiniteDialog(loadingMessage);
        return CompletableFuture.runAsync(task, backgroundExecutor)
                .handle((unused, throwable) -> {
                    dialogService.hideInfiniteDialog();
                    return throwable;
                })
                .thenCompose(throwable -> {
                    if (throwable == null) {
                        return dialogService.showInfoAsync(successMessage);
                    }
                    return dialogService.showErrorAsync(failMessage, throwable);
                });

    }
}
