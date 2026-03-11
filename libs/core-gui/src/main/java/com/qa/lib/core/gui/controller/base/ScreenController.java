package com.qa.lib.core.gui.controller.base;

import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;

public abstract class ScreenController<TViewModel extends ScreenViewModel> extends MvvmController<TViewModel> implements IParameterReceiver {
    protected Object navigationParameter = null;

    public ScreenController(TViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void setNavigationParameter(Object parameter) {
        navigationParameter = parameter;
        viewModel.setNavigationParameter(navigationParameter);
    }

    @Override
    protected void initialize() {
        super.initialize();
        viewModel.onInitialize();
    }
}
