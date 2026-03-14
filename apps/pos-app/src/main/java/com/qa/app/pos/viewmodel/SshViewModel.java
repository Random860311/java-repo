package com.qa.app.pos.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.ssh.gui.viewmodel.TargetJumpViewModel;
import com.qa.lib.ssh.service.ssh.ISshService;
import com.qa.lib.ssh.service.ssh.SshJumpConfig;

public final class SshViewModel extends ScreenViewModel {
    private final Runnable confirmCommand = this::onConfirm;

    private final ISshService sshService;
    private final TargetJumpViewModel sshViewModel;

    @Inject
    public SshViewModel(INavigationService navigationService, TargetJumpViewModel sshViewModel, IDialogService dialogService, ISshService sshService) {
        super(navigationService, dialogService);
        this.sshViewModel = sshViewModel;
        this.sshService = sshService;
    }

    public Runnable getConfirmCommand() {
        return confirmCommand;
    }

    public TargetJumpViewModel getSshViewModel() {
        return sshViewModel;
    }

    private void onConfirm() {
        executeTask(
                () -> {
                    SshJumpConfig config = sshViewModel.onConfirm();
                    sshService.init(config);
                },
                i18nService.getString("pos.gui.ssh.connection.init"),
                i18nService.getString("pos.gui.ssh.connection.ok"),
                i18nService.getString("pos.gui.ssh.connection.fail")
        ).whenCompleteAsync((success, exception) -> {
            if (exception == null) navigationService.back();
        }, uiExecutor).exceptionally(throwable -> {
            logService.error(throwable.getMessage(), throwable);
            return null;
        });
    }
}
