package com.qa.lib.core.gui.controller.base;

import com.google.inject.Inject;
import com.qa.lib.core.IDisposable;
import com.qa.lib.core.service.log.ILogService;
import javafx.fxml.FXML;

public abstract class BaseController implements IDisposable {
    @Inject
    protected ILogService logService;

    @FXML
    protected void initialize() {

    }

    @Override
    public void dispose() {

    }
}
