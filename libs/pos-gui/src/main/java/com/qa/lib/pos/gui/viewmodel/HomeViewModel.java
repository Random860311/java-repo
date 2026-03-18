package com.qa.lib.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.gui.service.navigation.PosRoutes;

public final class HomeViewModel extends ScreenViewModel {
    private final Runnable showSettingsCommand = this::onShowSettings;
    private final Runnable showSshCommand = this::onShowSsh;

    @Inject
    public HomeViewModel(INavigationService navigationService, IDialogService dialogService) {
        super(navigationService, dialogService);
    }

    public Runnable getShowSettingsCommand() {
        return showSettingsCommand;
    }

    public Runnable getShowSshCommand() {
        return showSshCommand;
    }

    private void onShowSettings() {
        navigationService.navigateTo(PosRoutes.SETTINGS);
    }

    private void onShowSsh() { navigationService.navigateTo(PosRoutes.SSH);}
}
