package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.app.pos.gui.service.navigation.EViewId;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;

public final class MainViewModel extends ScreenViewModel {

    @Inject
    public MainViewModel(INavigationService navigationService, IDialogService dialogService) {
        super(navigationService, dialogService);

    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        navigationService.navigateTo(EViewId.HOME.ordinal());
    }
}
