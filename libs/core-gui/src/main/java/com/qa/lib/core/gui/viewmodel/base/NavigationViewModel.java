package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.navigation.INavigationService;

public abstract class NavigationViewModel extends BaseViewModel {
    protected final INavigationService navigationService;

    @Inject
    public NavigationViewModel(INavigationService navigationService) {
        super();
        this.navigationService = navigationService;
    }

    public void onInitialize() {

    }
}
