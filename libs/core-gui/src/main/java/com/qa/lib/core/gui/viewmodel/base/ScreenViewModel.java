package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.service.navigation.INavigationService;

public abstract class ScreenViewModel extends BaseViewModel implements IParameterReceiver {
    protected final INavigationService navigationService;
    protected Object navigationParameter = null;

    @Override
    public void setNavigationParameter(Object parameter) {
        navigationParameter = parameter;
    }


    @Inject
    public ScreenViewModel(INavigationService navigationService) {
        super();
        this.navigationService = navigationService;
    }

    public void onInitialize() {

    }
}
