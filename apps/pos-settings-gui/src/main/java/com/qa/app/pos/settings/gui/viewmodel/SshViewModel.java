package com.qa.app.pos.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.ssh.gui.viewmodel.TargetJumpViewModel;
import com.qa.lib.ssh.service.SshJumpConfig;

public class SshViewModel extends ScreenViewModel {
    private final Runnable confirmCommand = this::onConfirm;

    private final TargetJumpViewModel sshViewModel;

    @Inject
    public SshViewModel(INavigationService navigationService, TargetJumpViewModel sshViewModel) {
        super(navigationService);
        this.sshViewModel = sshViewModel;

    }

    public Runnable getConfirmCommand() {
        return confirmCommand;
    }

    public TargetJumpViewModel getSshViewModel() {
        return sshViewModel;
    }

    private void onConfirm() {
        System.out.println("ssh confirm");
        SshJumpConfig config = sshViewModel.onConfirm();
    }
}
