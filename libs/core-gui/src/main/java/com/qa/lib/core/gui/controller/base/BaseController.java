package com.qa.lib.core.gui.controller.base;

import com.qa.lib.core.IDisposable;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import javafx.fxml.FXML;

public abstract class BaseController<TViewModel extends BaseViewModel> implements IDisposable {
    protected TViewModel viewModel;

    public BaseController() {}

    public BaseController(TViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public TViewModel getViewModel() {
        return viewModel;
    }

    @FXML
    protected void initialize() {

    }

    @Override
    public void dispose() {

    }
}
