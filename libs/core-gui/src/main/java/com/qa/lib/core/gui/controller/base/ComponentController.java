package com.qa.lib.core.gui.controller.base;

import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;

public abstract class ComponentController<TViewModel extends ComponentViewModel> extends MvvmController<TViewModel> {

    public ComponentController(TViewModel viewModel) {
        super(viewModel);
    }

    public ComponentController() {
        super();
    }

    public void setViewModel(TViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
