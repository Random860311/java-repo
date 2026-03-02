package com.qa.app.pos.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.service.navigation.EViewId;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.NavigationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class HomeViewModel extends NavigationViewModel {
    private final Runnable showSettingsCommand = this::showSettings;

    @Inject
    public HomeViewModel(INavigationService navigationService) {
        super(navigationService);
    }

    public Runnable getShowSettingsCommand() {
        return showSettingsCommand;
    }

    private void showSettings() {
        navigationService.navigateTo(EViewId.POS_SETTINGS.ordinal());
    }
}
