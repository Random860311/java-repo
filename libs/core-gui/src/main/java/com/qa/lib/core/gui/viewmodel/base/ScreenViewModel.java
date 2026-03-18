package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.exception.AppException;
import com.qa.lib.core.gui.ILifeCycle;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.util.TaskExecutionResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class ScreenViewModel extends BaseViewModel implements IParameterReceiver, ILifeCycle {
    private final Runnable closeCommand = this::onClose;

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

    public void onInitialize() { }

    @Override
    public void onResume() { }

    @Override
    public void onStop() { }

    public Runnable getCloseCommand() {
        return closeCommand;
    }

    protected void onClose() {
        CompletableFuture.runAsync(navigationService::back, uiExecutor);
    }

    protected <T> CompletableFuture<T> executeTask(
            Supplier<T> task,
            String loadingMessage,
            String successMessage,
            String failMessage
    ) {
        dialogService.showInfiniteDialog(loadingMessage);

        return CompletableFuture
                .supplyAsync(task, backgroundExecutor)
                .handle(TaskExecutionResult::new)
                .thenCompose(executionResult -> {
                    dialogService.hideInfiniteDialog();
                    CompletableFuture<T> future = new CompletableFuture<>();
                    if (executionResult.throwable == null) {
                        if (successMessage == null) {
                            return CompletableFuture.completedFuture(executionResult.result);
                        }
                        dialogService.showInfoAsync(successMessage).thenApply(unused -> future.complete(executionResult.result));

                    } else {
                        String message = executionResult.throwable instanceof AppException ? ((AppException) executionResult.throwable).getUserMessage() : failMessage;
                        dialogService.showErrorAsync(message, executionResult.throwable).whenComplete((result, throwable) -> {
                            future.completeExceptionally(executionResult.throwable);
                        });
                    }
                    return future;
                });
    }

    protected CompletableFuture<Void> executeTask(Runnable task, String loadingMessage, String successMessage, String failMessage) {
        return executeTask(() ->  {
            task.run();
            return null;
        }, loadingMessage, successMessage, failMessage);
    }


}
