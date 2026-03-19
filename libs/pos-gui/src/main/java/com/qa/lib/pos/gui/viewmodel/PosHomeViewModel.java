package com.qa.lib.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.gui.service.navigation.PosRoutes;
import com.qa.lib.ssh.gui.service.navigation.SshRoutes;

public final class PosHomeViewModel extends ScreenViewModel {
    private final Runnable showSettingsCommand = this::onShowSettings;


    @Inject
    public PosHomeViewModel(INavigationService navigationService, IDialogService dialogService) {
        super(navigationService, dialogService);
    }

    public Runnable getShowSettingsCommand() {
        return showSettingsCommand;
    }



    private void onShowSettings() {
        navigationService.navigateTo(PosRoutes.SETTINGS);
    }


}
