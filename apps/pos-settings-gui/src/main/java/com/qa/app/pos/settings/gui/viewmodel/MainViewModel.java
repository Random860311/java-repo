package com.qa.app.pos.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.service.navigation.EViewId;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import com.qa.lib.core.gui.viewmodel.base.NavigationViewModel;
import com.qa.lib.pos.settings.service.config.IPosConfigFileService;
import com.qa.lib.settings.gui.viewmodel.FileListViewModel;

public class MainViewModel extends NavigationViewModel {

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
