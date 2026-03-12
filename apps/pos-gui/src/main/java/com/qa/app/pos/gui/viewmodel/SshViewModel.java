package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.core.qualifiers.BackgroundThread;
import com.qa.lib.ssh.gui.viewmodel.TargetJumpViewModel;
import com.qa.lib.ssh.service.ssh.SshJumpConfig;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SshViewModel extends ScreenViewModel {
    private final Runnable confirmCommand = this::onConfirm;

    private final TargetJumpViewModel sshViewModel;
    private final Executor backgroundExecutor;
    private final IDialogService dialogService;

    @Inject
    public SshViewModel(INavigationService navigationService, TargetJumpViewModel sshViewModel, @BackgroundThread Executor backgroundExecutor, IDialogService dialogService) {
        super(navigationService);
        this.sshViewModel = sshViewModel;
        this.backgroundExecutor = backgroundExecutor;
        this.dialogService = dialogService;
    }

    public Runnable getConfirmCommand() {
        return confirmCommand;
    }

    public TargetJumpViewModel getSshViewModel() {
        return sshViewModel;
    }

    private void onConfirm() {
        logService.debug("ssh confirm");
        SshJumpConfig config = sshViewModel.onConfirm();

        CompletableFuture.runAsync(() -> {
            try {
                dialogService.showInfiniteDialog();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).thenAcceptAsync(result -> {
            dialogService.hideInfiniteDialog();
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
}
