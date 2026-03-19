package com.qa.app.main.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.gui.service.navigation.PosRoutes;
import com.qa.lib.ssh.gui.service.navigation.SshRoutes;

public final class HomeViewModel extends ScreenViewModel {
    private final Runnable showPosCommand = this::onShowPos;
    private final Runnable showSshCommand = this::onShowSsh;

    @Inject
    public HomeViewModel(INavigationService navigationService, IDialogService dialogService) {
        super(navigationService, dialogService);
    }

    public Runnable getShowPosCommand() {
        return showPosCommand;
    }
    public Runnable getShowSshCommand() {
        return showSshCommand;
    }

    private void onShowPos() {
        navigationService.navigateTo(PosRoutes.HOME);
    }

    private void onShowSsh() { navigationService.navigateTo(SshRoutes.SSH);}
}
