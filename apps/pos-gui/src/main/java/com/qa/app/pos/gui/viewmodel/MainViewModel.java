package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.app.pos.gui.service.navigation.EViewId;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;

public class MainViewModel extends ScreenViewModel {

    @Inject
    public MainViewModel(INavigationService navigationService) {
        super(navigationService);

    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        navigationService.navigateTo(EViewId.HOME.ordinal());
    }
}
