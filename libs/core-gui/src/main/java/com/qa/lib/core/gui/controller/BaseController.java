package com.qa.lib.core.gui.controller;

import com.qa.lib.core.IDisposable;
import javafx.fxml.FXML;

public abstract class BaseController implements IParameterReceiver, IDisposable {
    protected Object navigationParameter = null;

    @Override
    public void setNavigationParameter(Object parameter) {
        navigationParameter = parameter;
    }

    @FXML
    protected void initialize() {

    }

    @Override
    public void dispose() {

    }
}
