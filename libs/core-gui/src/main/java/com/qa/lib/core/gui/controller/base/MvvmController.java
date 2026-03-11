package com.qa.lib.core.gui.controller.base;

import com.qa.lib.core.IDisposable;
import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import javafx.fxml.FXML;

public abstract class MvvmController<TViewModel extends BaseViewModel> extends BaseController {
    protected TViewModel viewModel;

    public MvvmController() {}

    public MvvmController(TViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public TViewModel getViewModel() {
        return viewModel;
    }


}
